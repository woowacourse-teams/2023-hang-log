package hanglog.currency.domain;

import java.util.Optional;

public class DefaultCurrency {

    private static Currency oldestCurrency;

    private DefaultCurrency() {
    }

    public static Optional<Currency> getOldestCurrency() {
        return Optional.ofNullable(oldestCurrency);
    }

    public static void setOldestCurrency(Currency oldestCurrency) {
        DefaultCurrency.oldestCurrency = oldestCurrency;
    }
}
