package hanglog.expense.fixture;

import hanglog.expense.domain.Currency;
import java.time.LocalDate;

public class CurrenciesFixture {

    public static final Currency DEFAULT_CURRENCY = new Currency(
            1L,
            LocalDate.of(2023, 1, 1),
            1300.0,
            1400.0,
            1600.0,
            900.0,
            170.0,
            110.0,
            120.0,
            110.0,
            120.0,
            1.0
    );
}
