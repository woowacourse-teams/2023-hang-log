package hanglog.trip.fixture;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.expense.fixture.AmountFixture.AMOUNT_10000;

import hanglog.expense.domain.Expense;

public final class ExpenseFixture {

    public static final Expense EURO_10000 = new Expense(
            1L,
            "EUR",
            AMOUNT_10000,
            EXPENSE_CATEGORIES.get(1)
    );

    public static final Expense JPY_10000 = new Expense(
            1L,
            "JPY",
            AMOUNT_10000,
            EXPENSE_CATEGORIES.get(3)
    );
}
