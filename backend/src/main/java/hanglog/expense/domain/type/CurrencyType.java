package hanglog.expense.domain.type;

import static hanglog.global.exception.ExceptionCode.INVALID_CURRENCY;

import hanglog.expense.domain.Currency;
import hanglog.global.exception.InvalidDomainException;
import java.util.Arrays;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum CurrencyType {

    USD("usd", Currency::getUsd),
    EUR("eur", Currency::getEur),
    GBP("gbp", Currency::getGbp),
    JPY("jpy", Currency::getUnitRateOfJpy),
    CNH("cnh", Currency::getCnh),
    CHF("chf", Currency::getChf),
    SGD("sgd", Currency::getSgd),
    THB("thb", Currency::getThb),
    HKD("hkd", Currency::getHkd),
    KRW("krw", Currency::getKrw);

    private final String code;
    private final Function<Currency, Double> getRate;

    CurrencyType(final String code, final Function<Currency, Double> getRate) {
        this.code = code;
        this.getRate = getRate;
    }

    public static double getCurrencyRate(final String currencyCode, final Currency currency) {
        return mapping(currencyCode).getRate.apply(currency);
    }

    public static CurrencyType mapping(final String currencyCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(currencyCode.toLowerCase().replace("(100)", "")))
                .findAny()
                .orElseThrow(() -> new InvalidDomainException(INVALID_CURRENCY));
    }

    public static boolean provide(final String currencyCode) {
        return Arrays.stream(CurrencyType.values())
                .map(CurrencyType::getCode)
                .toList()
                .contains(currencyCode);
    }
}
