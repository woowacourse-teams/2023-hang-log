package hanglog.expense;

import hanglog.global.exception.BadRequestException;
import hanglog.global.exception.ExceptionCode;
import java.util.Arrays;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum Currency {

    usd("usd", Currencies::getUsd),
    eur("eur", Currencies::getEur),
    gbp("gbp", Currencies::getGbp),
    jpy("jpy", Currencies::getJpy),
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
        return Arrays.stream(values()).filter(it -> it.is(currency))
                .findAny().orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_RATING))
                .getRate.apply(currencies);
    }

    private boolean is(final String currency) {
        return this.currency.equals(currency);
    }
}
