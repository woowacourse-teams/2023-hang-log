package hanglog.expense.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.category.Category;
import hanglog.expense.Currencies;
import hanglog.expense.Currency;
import hanglog.expense.Expense;
import hanglog.expense.dto.response.ExpenseGetResponse;
import hanglog.expense.repository.CurrenciesRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private static final Currencies DEFAULT_CURRENCIES = Currencies.ofDefault();
    private final TripRepository tripRepository;
    private final CurrenciesRepository currenciesRepository;
    private final TripCityRepository tripCityRepository;

    public ExpenseGetResponse getAllExpenses(final long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final Currencies currencies = currenciesRepository.findCurrenciesBySearchDate(trip.getStartDate())
                .orElse(DEFAULT_CURRENCIES);

        final Map<DayLog, Integer> dayLogTotalAmounts = new HashMap<>();
        final Map<Category, Integer> categoryTotalAmounts = new HashMap<>();
        final List<TripCity> cities = tripCityRepository.findByTripId(tripId);

        for (final DayLog dayLog : trip.getDayLogs()) {
            calculateAmounts(dayLog, currencies, dayLogTotalAmounts, categoryTotalAmounts);
        }

        final int totalAmount = dayLogTotalAmounts.values().stream().reduce(Integer::sum).orElse(0);

        return ExpenseGetResponse.of(
                trip,
                totalAmount,
                cities,
                categoryTotalAmounts,
                currencies,
                dayLogTotalAmounts
        );
    }

    private void calculateAmounts(
            final DayLog dayLog,
            final Currencies currencies,
            final Map<DayLog, Integer> dayLogIntegerMap,
            final Map<Category, Integer> categoryIntegerMap
    ) {
        for (final Item item : dayLog.getItems()) {
            final Expense expense = item.getExpense();
            add(dayLogIntegerMap, dayLog, changeToKRW(expense, currencies));
            add(categoryIntegerMap, expense.getCategory(), changeToKRW(expense, currencies));
        }
    }

    private int changeToKRW(final Expense expense, final Currencies currencies) {
        if (expense == null) {
            return 0;
        }
        final double rate = Currency.mappingCurrency(expense.getCurrency(), currencies);
        return (int) (expense.getAmount() * rate);
    }

    private <T> void add(final Map<T, Integer> map, final T key, final int value) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + value);
            return;
        }
        map.put(key, value);
    }
}
