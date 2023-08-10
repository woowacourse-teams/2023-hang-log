package hanglog.expense.dto.response;


import hanglog.category.dto.CategoryResponse;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.CategoryExpense;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryExpenseResponse {

    private final CategoryResponse category;
    private final Amount amount;
    private final BigDecimal percentage;

    public static CategoryExpenseResponse of(final CategoryExpense categoryExpense) {
        return new CategoryExpenseResponse(
                CategoryResponse.of(categoryExpense.getCategory()),
                categoryExpense.getAmount(),
                categoryExpense.getPercentage()
        );
    }
}
