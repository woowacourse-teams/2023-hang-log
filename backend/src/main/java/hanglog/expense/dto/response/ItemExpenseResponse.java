package hanglog.expense.dto.response;

import hanglog.category.dto.CategoryResponse;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemExpenseResponse {

    private final Long id;
    private final String currency;
    private final Amount amount;
    private final CategoryResponse category;

    public static ItemExpenseResponse of(final Expense expense) {
        return new ItemExpenseResponse(
                expense.getId(),
                expense.getCurrency(),
                expense.getAmount(),
                CategoryResponse.of(expense.getCategory())
        );
    }
}
