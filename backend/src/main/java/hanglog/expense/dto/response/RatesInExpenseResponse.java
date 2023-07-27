package hanglog.expense.dto.response;

import hanglog.expense.domain.Currency;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RatesInExpenseResponse {

    private final LocalDate date;
    private final List<CurrencyInExpenseResponse> values;

    public static RatesInExpenseResponse of(final Currency currency) {
        return new RatesInExpenseResponse(
                currency.getSearchDate(),
                CurrencyInExpenseResponse.of(currency)
        );
    }
}
