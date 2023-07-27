package hanglog.expense.dto.response;

import hanglog.expense.domain.Currency;
import hanglog.expense.domain.type.CurrencyCodeType;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrencyInExpenseResponse {

    private final String currency;
    private final double rate;

    public static List<CurrencyInExpenseResponse> of(final Currency currency) {
        return Arrays.stream(CurrencyCodeType.values())
                .map(value -> new CurrencyInExpenseResponse(value.getCode(), value.getGetRate().apply(currency)))
                .toList();
    }
}
