package hanglog.expense.fixture;

import static hanglog.category.fixture.CategoryFixture.ACCOMMODATION;
import static hanglog.category.fixture.CategoryFixture.CULTURE;
import static hanglog.category.fixture.CategoryFixture.ETC;
import static hanglog.category.fixture.CategoryFixture.FOOD;
import static hanglog.category.fixture.CategoryFixture.SHOPPING;
import static hanglog.category.fixture.CategoryFixture.TRANSPORTATION;

import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;

public class ExpenseFixture {

    protected static final Expense KRW_100_FOOD_EXPENSE = new Expense(
            1L,
            "KRW",
            new Amount(100),
            FOOD
    );

    protected static final Expense JPY_100_CULTURE_EXPENSE = new Expense(
            2L,
            "JPY",
            new Amount(100),
            CULTURE
    );

    protected static final Expense EUR_100_SHOPPING_EXPENSE = new Expense(
            3L,
            "EUR",
            new Amount(100),
            SHOPPING
    );

    protected static final Expense USD_100_ACCOMMODATION_EXPENSE = new Expense(
            4L,
            "USD",
            new Amount(100),
            ACCOMMODATION
    );

    protected static final Expense CNY_100_TRANSPORTATION_EXPENSE = new Expense(
            5L,
            "CNY",
            new Amount(100),
            TRANSPORTATION
    );

    protected static final Expense GBP_100_ETC_EXPENSE = new Expense(
            6L,
            "GBP",
            new Amount(100),
            ETC
    );
}
