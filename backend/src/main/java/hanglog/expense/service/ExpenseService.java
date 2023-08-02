package hanglog.expense.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.global.exception.ExceptionCode.NO_CURRENCY_DATA;

import hanglog.category.domain.Category;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.Currency;
import hanglog.expense.domain.DayLogExpense;
import hanglog.expense.domain.Expense;
import hanglog.expense.domain.repository.CurrencyRepository;
import hanglog.expense.domain.type.CurrencyType;
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

    public TripExpenseResponse getAllExpenses(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final Currency currency = currencyRepository.findTopByDateLessThanEqualOrderByDateDesc(trip.getStartDate())
                .orElse(findOldestCurrency());

        final Map<DayLog, Integer> dayLogAmounts = new HashMap<>();
        final Map<Category, Integer> categoryAmounts = new HashMap<>();
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
                .orElseThrow(() -> new BadRequestException(NO_CURRENCY_DATA));
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
                final int dayLogAmount = dayLogAmounts.getOrDefault(dayLog, 0);
                dayLogAmounts.put(dayLog, dayLogAmount + changeToKRW(expense.get(), currency));

                final int categoryAmount = categoryAmounts.getOrDefault(expense.get().getCategory(), 0);
                categoryAmounts.put(expense.get().getCategory(), categoryAmount + changeToKRW(expense.get(), currency));
            }
        }
    }

    private int changeToKRW(final Expense expense, final Currency currency) {
        final double rate = CurrencyType.mappingCurrency(expense.getCurrency(), currency);
        return (int) (expense.getAmount() * rate);
    }

    private int calculateTotalAmount(final Map<DayLog, Integer> dayLogAmounts) {
        return dayLogAmounts.values().stream()
                .reduce(Integer::sum)
                .orElse(0);
    }
}
