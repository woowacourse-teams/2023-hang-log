package hanglog.expense.domain;

import hanglog.category.domain.Category;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;

@Getter
public class CategoryExpense {

    private static final int PERCENTAGE_CONSTANT = 100;

    private final Category category;
    private final Amount amount;
    private final BigDecimal percentage;

    public CategoryExpense(final Category category, final Amount amount, final Amount totalAmount) {
        this.category = category;
        this.amount = amount;
        this.percentage = calculatePercentage(amount, totalAmount);
    }

    private BigDecimal calculatePercentage(final Amount amount, final Amount totalAmount) {
        if (totalAmount.equals(new Amount(0))) {
            return BigDecimal.ZERO;
        }
        return amount.multiply(PERCENTAGE_CONSTANT).divide(totalAmount).getAmount();
    }
}
