package hanglog.currency.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CurrencyListResponse {

    final List<CurrencyResponse> currencies;
    final Long lastPageIndex;
}
