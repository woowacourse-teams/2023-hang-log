package hanglog.expense;

import static hanglog.global.exception.ExceptionCode.INVALID_CURRENCY;

import hanglog.global.exception.InvalidDomainException;
import java.util.Arrays;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum Currency {

    usd("usd", Currencies::getUsd),
    eur("eur", Currencies::getEur),
    gbp("gbp", Currencies::getGbp),
    jpy("jpy", Currencies::getUnitRateOfJpy),
    cny("cny", Currencies::getCny),
    chf("chf", Currencies::getChf),
    sgd("sgd", Currencies::getSgd),
    thb("thb", Currencies::getThb),
    hkd("hkd", Currencies::getHkd),
    krw("krw", Currencies::getKrw);
    private final String currency;
    private final Function<Currencies, Double> getRate;

    Currency(final String currency, final Function<Currencies, Double> getRate) {
        this.currency = currency;
        this.getRate = getRate;
    }

    public static double mappingCurrency(final String currency, final Currencies currencies) {
        return Arrays.stream(values())
                .filter(value -> value.is(currency.toLowerCase()))
                .findAny().orElseThrow(() -> new InvalidDomainException(INVALID_CURRENCY))
                .getRate.apply(currencies);
    }

    private boolean is(final String currency) {
        return this.currency.equals(currency);
    }
}
