package hanglog.category.domain;

import java.util.List;
import java.util.Optional;

public class ExpenseCategories {

    private static List<Category> expenseCategories;

    private ExpenseCategories() {
    }

    public static Optional<List<Category>> get() {
        return Optional.ofNullable(expenseCategories);
    }

    public static void init(final List<Category> expenseCategories) {
        ExpenseCategories.expenseCategories = expenseCategories;
    }
}
