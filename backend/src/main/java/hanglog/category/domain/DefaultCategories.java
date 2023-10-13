package hanglog.category.domain;

import java.util.List;
import java.util.Optional;

public class DefaultCategories {

    private static List<Category> expenseCategories;

    private DefaultCategories() {
    }

    public static Optional<List<Category>> getExpenseCategories() {
        return Optional.ofNullable(expenseCategories);
    }

    public static void setExpenseCategories(final List<Category> expenseCategories) {
        DefaultCategories.expenseCategories = expenseCategories;
    }
}
