package hanglog.expense.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CurrencyResponse {

    @JsonProperty("result")
    private int result;

    @JsonProperty("cur_unit")
    private String currencyCode;

    @JsonProperty("deal_bas_r")
    private String rate;
}
