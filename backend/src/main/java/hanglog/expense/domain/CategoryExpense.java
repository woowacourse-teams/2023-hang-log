package hanglog.expense.domain;

import hanglog.category.domain.Category;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;

@Getter
public class CategoryExpense {

    private static final int PERCENTAGE_CONSTANT = 100;

    private final Category category;
    private final int amount;
    private final BigDecimal percentage;

    public CategoryExpense(
            final Category category,
            final int amount,
            final int totalAmount
    ) {
        this.category = category;
        this.amount = amount;
        this.percentage = calculatePercentage(amount, totalAmount);
    }

    private BigDecimal calculatePercentage(final int amount, final int totalAmount) {
        if (totalAmount == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf((double) PERCENTAGE_CONSTANT * amount / totalAmount)
                .setScale(2, RoundingMode.CEILING);
    }
}
