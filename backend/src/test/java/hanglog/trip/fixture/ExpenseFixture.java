package hanglog.trip.fixture;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;

import hanglog.expense.Expense;

public final class ExpenseFixture {

    public static final Expense EURO_10000 = new Expense(
            1L,
            "EUR",
            10000.0,
            EXPENSE_CATEGORIES.get(1)
    );

    public static final Expense JPY_10000 = new Expense(
            1L,
            "JPY",
            10000.0,
            EXPENSE_CATEGORIES.get(3)
    );
}
