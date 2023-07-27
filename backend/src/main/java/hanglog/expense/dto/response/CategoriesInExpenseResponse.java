package hanglog.expense.dto.response;


import hanglog.category.domain.Category;
import hanglog.category.dto.CategoryResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoriesInExpenseResponse {

    private final CategoryResponse category;
    private final int amount;
    private final BigDecimal percentage;

    public static List<CategoriesInExpenseResponse> of(final Map<Category, Integer> categories) {
        final int totalAmount = categories.values().stream()
                .reduce(Integer::sum)
                .orElse(0);
        if (totalAmount == 0) {
            return categories.entrySet().stream()
                    .map(entry -> getResponse(0, entry))
                    .toList();
        }
        return categories.entrySet().stream()
                .map(entry -> getResponse((double) entry.getValue() / totalAmount, entry))
                .toList();
    }

    private static CategoriesInExpenseResponse getResponse(
            final double amount,
            final Entry<Category, Integer> entry
    ) {
        return new CategoriesInExpenseResponse(
                CategoryResponse.of(entry.getKey()),
                entry.getValue(),
                BigDecimal.valueOf(amount * 100).setScale(2, RoundingMode.CEILING)//.setScale(2)
        );
    }
}
