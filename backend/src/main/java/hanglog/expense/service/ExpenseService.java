package hanglog.expense.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.category.domain.Category;
import hanglog.category.dto.CategoryResponse;
import hanglog.expense.domain.Currency;
import hanglog.expense.domain.Expense;
import hanglog.expense.domain.repository.CurrencyRepository;
import hanglog.expense.domain.type.CurrencyCodeType;
import hanglog.expense.dto.response.CategoryExpenseResponse;
import hanglog.expense.dto.response.DayLogExpenseResponse;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private static final int PERCENTAGE_CONSTANT = 100;

    //  TODO: 추후 Currency 데이터 생길시 deafault 값 추가
    private static final Currency DEFAULT_CURRENCY = Currency.getDefaultCurrency();
    private final TripRepository tripRepository;
    private final CurrencyRepository currencyRepository;
    private final TripCityRepository tripCityRepository;

    public TripExpenseResponse getAllExpenses(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final Currency currency = currencyRepository.findByDate(trip.getStartDate())
                .orElse(DEFAULT_CURRENCY);

        final Map<DayLog, Integer> dayLogTotalAmounts = new HashMap<>();
        final Map<Category, Integer> categoryTotalAmounts = new HashMap<>();
        final List<TripCity> cities = tripCityRepository.findByTripId(tripId);

        for (final DayLog dayLog : trip.getDayLogs()) {
            calculateAmounts(dayLog, currency, dayLogTotalAmounts, categoryTotalAmounts);
        }

        final int totalAmount = dayLogTotalAmounts.values().stream()
                .reduce(Integer::sum)
                .orElse(0);
        final List<CategoryExpenseResponse> categoryExpenseResponses = categoryTotalAmounts.entrySet().stream()
                .map(entry -> new CategoryExpenseResponse(
                                CategoryResponse.of(entry.getKey()),
                                entry.getValue(),
                                getCategoryAmountPercentage(totalAmount, entry.getValue())
                        )
                )
                .toList();
        final List<DayLogExpenseResponse> dayLogExpenseResponses = dayLogTotalAmounts.entrySet().stream()
                .map(entry -> DayLogExpenseResponse.of(entry.getKey(), entry.getValue()))
                .toList();

        return TripExpenseResponse.of(
                trip,
                totalAmount,
                cities,
                categoryExpenseResponses,
                currency,
                dayLogExpenseResponses
        );
    }

    private BigDecimal getCategoryAmountPercentage(final int totalAmount, final int categoryAmount) {
        if (totalAmount == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf((double) PERCENTAGE_CONSTANT * categoryAmount / totalAmount)
                .setScale(2, RoundingMode.CEILING);
    }

    private void calculateAmounts(
            final DayLog dayLog,
            final Currency currency,
            final Map<DayLog, Integer> dayLogTotalAmounts,
            final Map<Category, Integer> categoryTotalAmounts
    ) {
        for (final Item item : dayLog.getItems()) {
            final Expense expense = item.getExpense();

            final int dayLogTotalAmount = dayLogTotalAmounts.getOrDefault(dayLog, 0);
            dayLogTotalAmounts.put(dayLog, dayLogTotalAmount + changeToKRW(expense, currency));

            final int categoryTotalAmount = categoryTotalAmounts.getOrDefault(expense.getCategory(), 0);
            categoryTotalAmounts.put(expense.getCategory(), categoryTotalAmount + changeToKRW(expense, currency));
        }
    }

    private int changeToKRW(final Expense expense, final Currency currency) {
        if (expense == null) {
            return 0;
        }
        final double rate = CurrencyCodeType.mappingCurrency(expense.getCurrency(), currency);
        return (int) (expense.getAmount() * rate);
    }
}
