package hanglog.currency.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.currency.domain.Currency;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CurrencyResponse {

    private final Long id;
    private final LocalDate date;
    private final Double usd;
    private final Double eur;
    private final Double gbp;
    private final Double jpy;
    private final Double cny;
    private final Double chf;
    private final Double sgd;
    private final Double thb;
    private final Double hkd;
    private final Double krw;

    public static CurrencyResponse of(final Currency currency) {
        return new CurrencyResponse(
                currency.getId(),
                currency.getDate(),
                currency.getUsd(),
                currency.getEur(),
                currency.getGbp(),
                currency.getJpy(),
                currency.getCny(),
                currency.getChf(),
                currency.getSgd(),
                currency.getThb(),
                currency.getHkd(),
                currency.getKrw()
        );
    }
}
