package hanglog.expense.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrencyRateResponse {

    private final String currency;
    private final double rate;
}
