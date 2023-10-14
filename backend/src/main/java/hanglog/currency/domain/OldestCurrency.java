package hanglog.currency.domain;

import java.util.Optional;

public class OldestCurrency {

    private static Currency oldestCurrency;

    private OldestCurrency() {
    }

    public static Optional<Currency> get() {
        return Optional.ofNullable(oldestCurrency);
    }

    public static void init(final Currency oldestCurrency) {
        OldestCurrency.oldestCurrency = oldestCurrency;
    }
}
