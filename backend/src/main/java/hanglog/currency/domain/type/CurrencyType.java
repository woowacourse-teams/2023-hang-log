package hanglog.currency.domain.type;

import static hanglog.global.exception.ExceptionCode.INVALID_CURRENCY;

import hanglog.currency.domain.Currency;
import hanglog.global.exception.InvalidDomainException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum CurrencyType {

    USD("usd", Currency::getUsd),
    EUR("eur", Currency::getEur),
    GBP("gbp", Currency::getGbp),
    JPY("jpy", Currency::getUnitRateOfJpy),
    CNY("cny", Currency::getCny),
    CNY_FROM_API("cnh", Currency::getCny),
    CHF("chf", Currency::getChf),
    SGD("sgd", Currency::getSgd),
    THB("thb", Currency::getThb),
    HKD("hkd", Currency::getHkd),
    KRW("krw", Currency::getKrw);

    private static final String JPY_UNIT_STRING = "(100)";
    private static final String CNY_API_CODE = "cnh";

    private final String code;
    private final Function<Currency, Double> rate;

    CurrencyType(final String code, final Function<Currency, Double> rate) {
        this.code = code;
        this.rate = rate;
    }

    public static double getMappedCurrencyRate(final String currencyCode, final Currency currency) {
        return getMappedCurrencyType(currencyCode).rate.apply(currency);
    }

    public static CurrencyType getMappedCurrencyType(final String currencyCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(currencyCode.toLowerCase().replace(JPY_UNIT_STRING, "")))
                .findAny()
                .orElseThrow(() -> new InvalidDomainException(INVALID_CURRENCY));
    }

    public static boolean beProvided(final String currencyCode) {
        final List<String> values = Arrays.stream(CurrencyType.values())
                .map(CurrencyType::getCode)
                .toList();
        return values.contains(currencyCode.toLowerCase().replace(JPY_UNIT_STRING, ""));
    }

    public double getCurrencyRate(final Currency currency) {
        return this.rate.apply(currency);
    }
}
