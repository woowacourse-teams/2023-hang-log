package hanglog.expense.dto.response;

import hanglog.expense.Expense;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpenseInItemResponse {

    private final long id;
    private final String currency;
    private final double amount;
    private final CategoryInExpenseResponse category;

    public static ExpenseInItemResponse of(final Expense expense) {
        return new ExpenseInItemResponse(
                expense.getId(),
                expense.getCurrency(),
                expense.getAmount(),
                CategoryInExpenseResponse.of(expense.getCategory())
        );
    }
}
