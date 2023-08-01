package hanglog.expense.dto.response;

import hanglog.expense.domain.Currency;
import hanglog.expense.domain.type.CurrencyType;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrencyRateResponse {

    private final String currency;
    private final double rate;

    public static List<CurrencyRateResponse> of(final Currency currency) {
        return Arrays.stream(CurrencyType.values())
                .map(value -> new CurrencyRateResponse(value.getCode(), value.getGetRate().apply(currency)))
                .toList();
    }
}
