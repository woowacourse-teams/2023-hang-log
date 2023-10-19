package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CURRENCY_DATA;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.category.domain.Category;
import hanglog.category.domain.ExpenseCategories;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.currency.domain.Currency;
import hanglog.currency.domain.OldestCurrency;
import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.currency.domain.type.CurrencyType;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.Expense;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.DayLogExpense;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.LedgerResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LedgerService {

    private final TripRepository tripRepository;
    private final CurrencyRepository currencyRepository;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public LedgerResponse getAllExpenses(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final Currency currency = currencyRepository.findTopByDateLessThanEqualOrderByDateDesc(trip.getStartDate())
                .orElseGet(this::findOldestCurrency);

        final Map<DayLog, Amount> dayLogAmounts = getDayLogAmounts(trip.getDayLogs());
        final Map<Category, Amount> categoryAmounts = getCategoryAmounts();
        final List<City> cities = cityRepository.findCitiesByTripId(tripId);

        for (final DayLog dayLog : trip.getDayLogs()) {
            calculateAmounts(dayLog, currency, dayLogAmounts, categoryAmounts);
        }

        final Amount totalAmount = calculateTotalAmount(dayLogAmounts);

        final List<CategoryExpense> categoryExpenses = categoryAmounts.entrySet().stream()
                .map(entry -> new CategoryExpense(entry.getKey(), entry.getValue(), totalAmount))
                .sorted((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()))
                .toList();

        final List<DayLogExpense> dayLogExpenses = dayLogAmounts.entrySet().stream()
                .map(entry -> new DayLogExpense(entry.getKey(), entry.getValue()))
                .toList();

        return LedgerResponse.of(
                trip,
                totalAmount,
                cities,
                categoryExpenses,
                currency,
                dayLogExpenses
        );
    }

    private Currency findOldestCurrency() {
        final Optional<Currency> oldestCurrency = OldestCurrency.get();
        if (oldestCurrency.isPresent()) {
            return oldestCurrency.get();
        }
        final Currency currency = currencyRepository.findTopByOrderByDateAsc()
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CURRENCY_DATA));
        OldestCurrency.init(currency);
        return currency;
    }

    private void calculateAmounts(
            final DayLog dayLog,
            final Currency currency,
            final Map<DayLog, Amount> dayLogAmounts,
            final Map<Category, Amount> categoryAmounts
    ) {
        dayLog.getItems().stream()
                .map(item -> Optional.ofNullable(item.getExpense()))
                .filter(Optional::isPresent)
                .forEach(expense -> {
                    final Amount KRWAmount = changeToKRW(expense.get(), currency);
                    final Amount dayLogAmount = dayLogAmounts.get(dayLog);
                    dayLogAmounts.put(dayLog, dayLogAmount.add(KRWAmount));
                    final Category category = expense.get().getCategory();
                    final Amount categoryAmount = categoryAmounts.get(category);
                    categoryAmounts.put(category, categoryAmount.add(KRWAmount));
                });
    }

    private Amount changeToKRW(final Expense expense, final Currency currency) {
        final double rate = CurrencyType.getMappedCurrencyRate(expense.getCurrency(), currency);
        return expense.getAmount().multiply(rate);
    }

    private Amount calculateTotalAmount(final Map<DayLog, Amount> dayLogAmounts) {
        return dayLogAmounts.values().stream()
                .reduce(Amount::add)
                .orElse(Amount.ZERO);
    }

    private Map<DayLog, Amount> getDayLogAmounts(final List<DayLog> dayLogs) {
        final Map<DayLog, Amount> dayLogAmounts = new LinkedHashMap<>();
        for (final DayLog dayLog : dayLogs) {
            dayLogAmounts.put(dayLog, Amount.ZERO);
        }
        return dayLogAmounts;
    }

    private Map<Category, Amount> getCategoryAmounts() {
        final Map<Category, Amount> categoryAmounts = new LinkedHashMap<>();
        for (final Category category : findCategories()) {
            categoryAmounts.put(category, Amount.ZERO);
        }
        return categoryAmounts;
    }

    private List<Category> findCategories() {
        final Optional<List<Category>> expenseCategories = ExpenseCategories.get();
        if (expenseCategories.isPresent()) {
            return expenseCategories.get();
        }
        final List<Category> categories = categoryRepository.findExpenseCategory();
        ExpenseCategories.init(categories);
        return categories;
    }
}
