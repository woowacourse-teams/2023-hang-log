package hanglog.currency.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CurrencyResponse {

    @JsonProperty("result")
    private int result;
    @JsonProperty("cur_unit")
    private String curUnit;
    @JsonProperty("cur_nm")
    private String curNm;
    @JsonProperty("ttb")
    private String ttb;
    @JsonProperty("tts")
    private String tts;
    @JsonProperty("deal_bas_r")
    private String dealBasR;
    @JsonProperty("bkpr")
    private String bkpr;
    @JsonProperty("yy_efee_r")
    private String yyEfeeR;
    @JsonProperty("ten_dd_efee_r")
    private String tneDdEfeeR;
    @JsonProperty("kftc_bkpr")
    private String kftcBkpr;
    @JsonProperty("kftc_deal_bas_r")
    private String kftcDealBasR;
}
