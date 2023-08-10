package hanglog.expense.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CURRENCY_DATA;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.currency.domain.Currency;
import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.currency.domain.type.CurrencyType;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.DayLogExpense;
import hanglog.expense.domain.Expense;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private final TripRepository tripRepository;
    private final CurrencyRepository currencyRepository;
    private final TripCityRepository tripCityRepository;
    private final CategoryRepository categoryRepository;

    public TripExpenseResponse getAllExpenses(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final Currency currency = currencyRepository.findTopByDateLessThanEqualOrderByDateDesc(trip.getStartDate())
                .orElse(findOldestCurrency());

        final Map<DayLog, Amount> dayLogAmounts = getDayLogAmounts(trip.getDayLogs());
        final Map<Category, Amount> categoryAmounts = getCategoryAmounts();
        final List<TripCity> tripCities = tripCityRepository.findByTripId(tripId);

        for (final DayLog dayLog : trip.getDayLogs()) {
            calculateAmounts(dayLog, currency, dayLogAmounts, categoryAmounts);
        }

        final Amount totalAmount = calculateTotalAmount(dayLogAmounts);

        final List<CategoryExpense> categoryExpenses = categoryAmounts.entrySet().stream()
                .map(entry -> new CategoryExpense(entry.getKey(), entry.getValue(), totalAmount))
                .toList();

        final List<DayLogExpense> dayLogExpenses = dayLogAmounts.entrySet().stream()
                .map(entry -> new DayLogExpense(entry.getKey(), entry.getValue()))
                .toList();

        return TripExpenseResponse.of(
                trip,
                totalAmount,
                tripCities,
                categoryExpenses,
                currency,
                dayLogExpenses
        );
    }

    private Currency findOldestCurrency() {
        return currencyRepository.findTopByOrderByDateAsc()
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CURRENCY_DATA));
    }

    private void calculateAmounts(
            final DayLog dayLog,
            final Currency currency,
            final Map<DayLog, Amount> dayLogAmounts,
            final Map<Category, Amount> categoryAmounts
    ) {
        for (final Item item : dayLog.getItems()) {
            final Optional<Expense> expense = Optional.ofNullable(item.getExpense());

            if (expense.isPresent()) {
                final Amount KRWAmount = changeToKRW(expense.get(), currency);

                final Amount dayLogAmount = dayLogAmounts.get(dayLog);
                dayLogAmounts.put(dayLog, dayLogAmount.add(KRWAmount));

                final Amount categoryAmount = categoryAmounts.get(expense.get().getCategory());
                categoryAmounts.put(expense.get().getCategory(), categoryAmount.add(KRWAmount));
            }
        }
    }

    private Amount changeToKRW(final Expense expense, final Currency currency) {
        final double rate = CurrencyType.getMappedCurrencyRate(expense.getCurrency(), currency);
        return expense.getAmount().multiply(rate);
    }

    private Amount calculateTotalAmount(final Map<DayLog, Amount> dayLogAmounts) {
        return dayLogAmounts.values().stream()
                .reduce(Amount::add)
                .orElse(new Amount(0));
    }

    private Map<DayLog, Amount> getDayLogAmounts(final List<DayLog> dayLogs) {
        final Map<DayLog, Amount> dayLogAmounts = new LinkedHashMap<>();
        for (final DayLog dayLog : dayLogs) {
            dayLogAmounts.put(dayLog, new Amount(0));
        }
        return dayLogAmounts;
    }

    private Map<Category, Amount> getCategoryAmounts() {
        final List<Category> categories = categoryRepository.findExpenseCategory();
        final Map<Category, Amount> categoryAmounts = new LinkedHashMap<>();
        for (final Category category : categories) {
            categoryAmounts.put(category, new Amount(0));
        }
        return categoryAmounts;
    }
}
