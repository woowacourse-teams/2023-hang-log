package hanglog.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SingleCurrencyResponse {

    @JsonProperty("cur_unit")
    private String code;

    @JsonProperty("deal_bas_r")
    private String rate;
}
