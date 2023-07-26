package hanglog.expense.fixture;

import hanglog.expense.Currencies;
import java.time.LocalDate;

public class CurrenciesFixture {

    public static final Currencies DEFAULT_CURRENCIES = new Currencies(
            1L,
            LocalDate.of(2023, 1, 1),
            1275.5,
            1411.09,
            1644.2,
            906.24,
            178.2,
            112.2,
            123.2,
            113.2,
            123.2,
            1.0
    );
}
