package hanglog.trip.fixture;

import hanglog.category.domain.Category;
import hanglog.expense.Expense;

public final class ExpenseFixture {

    public static final Expense EURO_10000 = new Expense(
            1L,
            "EURO",
            10000.0,
            new Category(1L, "문화", "culture")
    );
}
