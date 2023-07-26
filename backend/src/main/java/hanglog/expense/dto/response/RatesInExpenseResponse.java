package hanglog.expense.dto.response;

import hanglog.expense.Currencies;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RatesInExpenseResponse {

    private final LocalDate date;
    private final List<CurrencyInExpenseResponse> values;

    public static RatesInExpenseResponse of(final Currencies currencies) {
        return new RatesInExpenseResponse(
                currencies.getSearchDate(),
                CurrencyInExpenseResponse.of(currencies)
        );
    }
}
