package hanglog.expense.dto.response;

import hanglog.currency.domain.Currency;
import hanglog.currency.domain.type.CurrencyType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExchangeRateResponse {

    private final LocalDate date;
    private final List<CurrencyRateResponse> currencyRates;

    public static ExchangeRateResponse of(final Currency currency) {
        final List<CurrencyRateResponse> currencyRateResponses = Arrays.stream(CurrencyType.values())
                .map(type -> new CurrencyRateResponse(type.getCode(), type.getCurrencyRate(currency)))
                .toList();

        return new ExchangeRateResponse(
                currency.getDate(),
                currencyRateResponses
        );
    }
}
