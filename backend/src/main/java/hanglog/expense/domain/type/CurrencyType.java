package hanglog.expense.domain.type;

import static hanglog.global.exception.ExceptionCode.INVALID_CURRENCY;

import hanglog.expense.domain.Currency;
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
    CHF("chf", Currency::getChf),
    SGD("sgd", Currency::getSgd),
    THB("thb", Currency::getThb),
    HKD("hkd", Currency::getHkd),
    KRW("krw", Currency::getKrw);

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
        if (currencyCode.equals("cnh")) {
            return CNY;
        }
        return Arrays.stream(values())
                .filter(value -> value.code.equals(currencyCode.toLowerCase().replace("(100)", "")))
                .findAny()
                .orElseThrow(() -> new InvalidDomainException(INVALID_CURRENCY));
    }

    public static boolean provide(final String currencyCode) {
        final List<String> values = Arrays.stream(CurrencyType.values())
                .map(CurrencyType::getCode)
                .toList();
        return values.contains(currencyCode) || currencyCode.equals("cnh");
    }

    public double getCurrencyRate(final Currency currency) {
        return this.rate.apply(currency);
    }
}
