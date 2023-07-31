package hanglog.expense.dto.response;

import hanglog.expense.domain.Currency;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExchangeRateResponse {

    private final LocalDate date;
    private final List<CurrencyRateResponse> currencyRates;

    public static ExchangeRateResponse of(final Currency currency) {
        return new ExchangeRateResponse(
                currency.getDate(),
                CurrencyRateResponse.of(currency)
        );
    }
}
