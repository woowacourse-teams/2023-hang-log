package hanglog.currency.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CurrencyListResponse {

    private final List<CurrencyResponse> currencies;
    private final Long lastPageIndex;
}
