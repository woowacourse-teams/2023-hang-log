package hanglog.expense.dto.response;

import hanglog.category.Category;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoriesInExpenseResponse {

    private final CategoryInExpenseResponse category;
    private final int amount;
    private final double percentage;

    public static List<CategoriesInExpenseResponse> of(final Map<Category, Integer> categories) {
        final int totalAmount = categories.values().stream().reduce(Integer::sum).orElse(0);
        if (totalAmount == 0) {
            return categories.entrySet().stream()
                    .map(entry -> getResponse(0, entry))
                    .toList();
        }
        return categories.entrySet().stream()
                .map(entry -> getResponse(entry.getValue() / totalAmount, entry))
                .toList();
    }

    private static CategoriesInExpenseResponse getResponse(
            final int amount,
            final Entry<Category, Integer> entry
    ) {
        return new CategoriesInExpenseResponse(
                CategoryInExpenseResponse.of(entry.getKey()),
                entry.getValue(),
                amount
        );
    }
}
