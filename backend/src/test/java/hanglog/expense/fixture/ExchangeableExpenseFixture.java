package hanglog.expense.fixture;

import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static hanglog.expense.fixture.ExpenseFixture.CNY_100_TRANSPORTATION_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.EUR_100_SHOPPING_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.GBP_100_ETC_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.JPY_100_CULTURE_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.KRW_100_FOOD_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.USD_100_ACCOMMODATION_EXPENSE;

import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;

public class ExchangeableExpenseFixture {
    public static final ExchangeableExpense KRW_100_FOOD = new ExchangeableExpense(
            KRW_100_FOOD_EXPENSE,
            KRW_100_FOOD_EXPENSE.getAmount().multiply(DEFAULT_CURRENCY.getKrw())
    );

    public static final ExchangeableExpense JPY_100_CULTURE = new ExchangeableExpense(
            JPY_100_CULTURE_EXPENSE,
            JPY_100_CULTURE_EXPENSE.getAmount().multiply(DEFAULT_CURRENCY.getJpy())
    );

    public static final ExchangeableExpense EUR_100_SHOPPING = new ExchangeableExpense(
            EUR_100_SHOPPING_EXPENSE,
            EUR_100_SHOPPING_EXPENSE.getAmount().multiply(DEFAULT_CURRENCY.getEur())
    );

    public static final ExchangeableExpense USD_100_ACCOMMODATION = new ExchangeableExpense(
            USD_100_ACCOMMODATION_EXPENSE,
            USD_100_ACCOMMODATION_EXPENSE.getAmount().multiply(DEFAULT_CURRENCY.getUsd())
    );

    public static final ExchangeableExpense CNY_100_TRANSPORTATION = new ExchangeableExpense(
            CNY_100_TRANSPORTATION_EXPENSE,
            CNY_100_TRANSPORTATION_EXPENSE.getAmount().multiply(DEFAULT_CURRENCY.getCny())
    );

    public static final ExchangeableExpense GBP_100_ETC = new ExchangeableExpense(
            GBP_100_ETC_EXPENSE,
            GBP_100_ETC_EXPENSE.getAmount().multiply(DEFAULT_CURRENCY.getJpy())
    );

    public static class ExchangeableExpense {
        public Expense expense;
        public Amount exchangeAmount;

        public ExchangeableExpense(final Expense expense, final Amount exchangeAmount) {
            this.expense = expense;
            this.exchangeAmount = exchangeAmount;
        }
    }
}
