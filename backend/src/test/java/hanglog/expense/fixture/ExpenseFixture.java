package hanglog.expense.fixture;

import static hanglog.category.fixture.CategoryFixture.ACCOMMODATION;
import static hanglog.category.fixture.CategoryFixture.CULTURE;
import static hanglog.category.fixture.CategoryFixture.ETC;
import static hanglog.category.fixture.CategoryFixture.FOOD;
import static hanglog.category.fixture.CategoryFixture.SHOPPING;
import static hanglog.category.fixture.CategoryFixture.TRANSPORTATION;
import static hanglog.expense.fixture.AmountFixture.AMOUNT_100;

import hanglog.expense.domain.Expense;

public class ExpenseFixture {

    protected static final Expense KRW_100_FOOD_EXPENSE = new Expense(
            1L,
            "KRW",
            AMOUNT_100,
            FOOD
    );

    protected static final Expense JPY_100_CULTURE_EXPENSE = new Expense(
            2L,
            "JPY",
            AMOUNT_100,
            CULTURE
    );

    protected static final Expense EUR_100_SHOPPING_EXPENSE = new Expense(
            3L,
            "EUR",
            AMOUNT_100,
            SHOPPING
    );

    protected static final Expense USD_100_ACCOMMODATION_EXPENSE = new Expense(
            4L,
            "USD",
            AMOUNT_100,
            ACCOMMODATION
    );

    protected static final Expense CNY_100_TRANSPORTATION_EXPENSE = new Expense(
            5L,
            "CNY",
            AMOUNT_100,
            TRANSPORTATION
    );

    protected static final Expense GBP_100_ETC_EXPENSE = new Expense(
            6L,
            "GBP",
            AMOUNT_100,
            ETC
    );
}
