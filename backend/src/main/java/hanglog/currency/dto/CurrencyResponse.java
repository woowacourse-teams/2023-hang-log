package hanglog.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CurrencyResponse {

    @JsonProperty("cur_unit")
    private String currencyCode;

    @JsonProperty("deal_bas_r")
    private String rate;
}
