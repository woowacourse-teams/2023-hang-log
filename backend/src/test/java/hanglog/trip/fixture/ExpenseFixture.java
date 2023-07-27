package hanglog.trip.fixture;

import static hanglog.category.fixture.CategoryFixture.CULTURE;
import static hanglog.category.fixture.CategoryFixture.LODGING;

import hanglog.expense.Expense;

public final class ExpenseFixture {

    public static final Expense EURO_10000 = new Expense(
            1L,
            "EUR",
            10000.0,
            CULTURE
    );

    public static final Expense JPY_1000 = new Expense(
            1L,
            "JPY",
            1000.0,
            LODGING
    );
}
