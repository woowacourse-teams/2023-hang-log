package hanglog.expense.dto.response;

import hanglog.category.Category;
import hanglog.expense.Currencies;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpenseGetResponse {

    private final Long id;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int totalAmount;
    private final List<CategoriesInExpenseResponse> categories;
    private final RatesInExpenseResponse rates;
    private final List<DayLogInExpenseResponse> dayLogs;

    public static ExpenseGetResponse of(
            final Trip trip,
            final int totalAmount,
            final Map<Category, Integer> categories,
            final Currencies currencies,
            final Map<DayLog, Integer> dayLogs
    ) {
        return new ExpenseGetResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                totalAmount,
                CategoriesInExpenseResponse.of(categories),
                RatesInExpenseResponse.of(currencies),
                DayLogInExpenseResponse.of(dayLogs)
        );
    }
}
