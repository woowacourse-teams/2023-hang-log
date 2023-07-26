package hanglog.expense.dto.response;

import hanglog.expense.Currencies;
import hanglog.expense.Currency;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrencyInExpenseResponse {

    private final String currency;
    private final double rate;

    public static List<CurrencyInExpenseResponse> of(final Currencies currencies) {
        return Arrays.stream(Currency.values())
                .map(value -> new CurrencyInExpenseResponse(value.getCurrency(), value.getGetRate().apply(currencies)))
                .toList();
    }
}
