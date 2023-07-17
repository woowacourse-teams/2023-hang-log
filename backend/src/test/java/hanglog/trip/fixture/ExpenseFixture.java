package hanglog.trip.fixture;

import hanglog.category.Category;
import hanglog.expense.Expense;

public class ExpenseFixture {

    public static Expense expense = new Expense(
            "EURO",
            10000,
            new Category(1L, "문화", "culture")
    );
}
