package hanglog.expense.dto.response;


import hanglog.category.dto.CategoryResponse;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryExpenseResponse {

    private final CategoryResponse category;
    private final int amount;
    private final BigDecimal percentage;

}
