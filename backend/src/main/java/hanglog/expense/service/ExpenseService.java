package hanglog.expense.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CURRENCY_DATA;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.currency.domain.Currency;
import hanglog.currency.domain.type.CurrencyType;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.DayLogExpense;
import hanglog.expense.domain.Expense;
import hanglog.expense.domain.repository.CurrencyRepository;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.util.HashMap;
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

        final Map<DayLog, Integer> dayLogAmounts = getDayLogAmounts(trip.getDayLogs());
        final Map<Category, Integer> categoryAmounts = getCategoryAmounts();
        final List<TripCity> tripCities = tripCityRepository.findByTripId(tripId);

        for (final DayLog dayLog : trip.getDayLogs()) {
            calculateAmounts(dayLog, currency, dayLogAmounts, categoryAmounts);
        }

        final int totalAmount = calculateTotalAmount(dayLogAmounts);

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
            final Map<DayLog, Integer> dayLogAmounts,
            final Map<Category, Integer> categoryAmounts
    ) {
        for (final Item item : dayLog.getItems()) {
            final Optional<Expense> expense = Optional.ofNullable(item.getExpense());

            if (expense.isPresent()) {
                final int dayLogAmount = dayLogAmounts.get(dayLog);
                dayLogAmounts.put(dayLog, dayLogAmount + changeToKRW(expense.get(), currency));

                final int categoryAmount = categoryAmounts.get(expense.get().getCategory());
                categoryAmounts.put(expense.get().getCategory(), categoryAmount + changeToKRW(expense.get(), currency));
            }
        }
    }

    private int changeToKRW(final Expense expense, final Currency currency) {
        final double rate = CurrencyType.getMappedCurrencyRate(expense.getCurrency(), currency);
        return (int) (expense.getAmount() * rate);
    }

    private int calculateTotalAmount(final Map<DayLog, Integer> dayLogAmounts) {
        return dayLogAmounts.values().stream()
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Map<DayLog, Integer> getDayLogAmounts(final List<DayLog> dayLogs) {
        final Map<DayLog, Integer> dayLogAmounts = new HashMap<>();
        for (final DayLog dayLog : dayLogs) {
            dayLogAmounts.put(dayLog, 0);
        }
        return dayLogAmounts;
    }

    private Map<Category, Integer> getCategoryAmounts() {
        final List<Category> categories = categoryRepository.findExpenseCategory();
        final Map<Category, Integer> categoryAmounts = new HashMap<>();
        for (final Category category : categories) {
            categoryAmounts.put(category, 0);
        }
        return categoryAmounts;
    }
}
