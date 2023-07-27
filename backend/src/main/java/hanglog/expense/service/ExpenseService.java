package hanglog.expense.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.category.domain.Category;
import hanglog.expense.domain.Currency;
import hanglog.expense.domain.Expense;
import hanglog.expense.domain.repository.CurrencyRepository;
import hanglog.expense.domain.type.CurrencyCodeType;
import hanglog.expense.dto.response.ExpenseGetResponse;
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

    //  TODO: 추후 Currency 데이터 생길시 deafault 값 추가
    private static final Currency DEFAULT_CURRENCY = Currency.ofDefault();
    private final TripRepository tripRepository;
    private final CurrencyRepository currencyRepository;
    private final TripCityRepository tripCityRepository;

    public ExpenseGetResponse getAllExpenses(final long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final Currency currency = currencyRepository.findCurrenciesBySearchDate(trip.getStartDate())
                .orElse(DEFAULT_CURRENCY);

        final Map<DayLog, Integer> dayLogTotalAmounts = new HashMap<>();
        final Map<Category, Integer> categoryTotalAmounts = new HashMap<>();
        final List<TripCity> cities = tripCityRepository.findByTripId(tripId);

        for (final DayLog dayLog : trip.getDayLogs()) {
            calculateAmounts(dayLog, currency, dayLogTotalAmounts, categoryTotalAmounts);
        }

        final int totalAmount = dayLogTotalAmounts.values().stream().reduce(Integer::sum).orElse(0);

        return ExpenseGetResponse.of(
                trip,
                totalAmount,
                cities,
                categoryTotalAmounts,
                currency,
                dayLogTotalAmounts
        );
    }

    private void calculateAmounts(
            final DayLog dayLog,
            final Currency currency,
            final Map<DayLog, Integer> dayLogTotalAmounts,
            final Map<Category, Integer> categoryTotalAmounts
    ) {
        for (final Item item : dayLog.getItems()) {
            final Expense expense = item.getExpense();
            putKeyAndValue(dayLogTotalAmounts, dayLog, changeToKRW(expense, currency));
            putKeyAndValue(categoryTotalAmounts, expense.getCategory(), changeToKRW(expense, currency));
        }
    }

    private int changeToKRW(final Expense expense, final Currency currency) {
        if (expense == null) {
            return 0;
        }
        final double rate = CurrencyCodeType.mappingCurrency(expense.getCurrency(), currency);
        return (int) (expense.getAmount() * rate);
    }

    private <T> void putKeyAndValue(final Map<T, Integer> map, final T key, final int value) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + value);
            return;
        }
        map.put(key, value);
    }
}
