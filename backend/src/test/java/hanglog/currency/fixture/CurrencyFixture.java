package hanglog.currency.fixture;

import hanglog.currency.domain.Currency;
import java.time.LocalDate;

public class CurrencyFixture {

    public static final Currency CURRENCY_1 = new Currency(
            1L,
            LocalDate.of(2023, 7, 1),
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0,
            1.0
    );

    public static final Currency CURRENCY_2 = new Currency(
            2L,
            LocalDate.of(2023, 7, 2),
            2.0,
            2.0,
            2.0,
            2.0,
            2.0,
            2.0,
            2.0,
            2.0,
            2.0,
            2.0
    );
}
