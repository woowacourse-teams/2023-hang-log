package hanglog.trip.presentation.dto.response;

import hanglog.expense.Expense;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpenseResponse {
    private final Long id;
    private final String currency;
    private final Integer amount;
    private final CategoryResponse category;

    public static ExpenseResponse of(final Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getCurrency(),
                expense.getAmount(),
                CategoryResponse.of(expense.getCategory())
        );
    }
}
