package hanglog.integration.controller;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import hanglog.admin.domain.type.AdminType;
import hanglog.currency.dto.request.CurrencyRequest;
import hanglog.currency.dto.response.CurrencyListResponse;
import hanglog.login.domain.MemberTokens;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AdminCurrencyIntegrationTest extends AdminIntegrationTest {

    private static final CurrencyRequest CURRENCY_REQUEST = new CurrencyRequest(
            LocalDate.of(2099, 12, 31),
            1181.1,
            1281.96,
            142.78,
            1019.47,
            162.76,
            1139.06,
            820.17,
            34.89,
            142.78
    );

    @DisplayName("새로운 환율 정보를 추가할 수 있다.")
    @Test
    void createCurrency() {
        // given
        final MemberTokens adminMemberToken = getAdminMemberTokenBy(AdminType.ADMIN);

        // when
        final ExtractableResponse<Response> response = requestCreateCurrency(adminMemberToken, CURRENCY_REQUEST);
        final Long id = Long.parseLong(parseUri(response.header("Location")));

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                    softly.assertThat(id).isPositive();
                }
        );
    }

    @DisplayName("환율 정보를 페이지로 조회할 수 있다.")
    @Test
    void getCurrencies() {
        // given
        final MemberTokens adminMemberToken = getAdminMemberTokenBy(AdminType.ADMIN);
        requestCreateCurrency(adminMemberToken, CURRENCY_REQUEST);

        // when
        final ExtractableResponse<Response> response = requestGetCurrencies(adminMemberToken, 0, 10);
        final CurrencyListResponse currencyListResponse = response.as(CurrencyListResponse.class);
        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(currencyListResponse.getLastPageIndex()).isPositive();
                    softly.assertThat(currencyListResponse.getCurrencies()).isNotEmpty();
                    softly.assertThat(currencyListResponse.getCurrencies().get(0))
                            .usingRecursiveComparison()
                            .ignoringFields("id", "krw")
                            .isEqualTo(CURRENCY_REQUEST);
                }
        );
    }


    public static ExtractableResponse<Response> requestCreateCurrency(
            final MemberTokens memberTokens,
            final CurrencyRequest request
    ) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(request)
                .when().post("/admin/currencies")
                .then().log().all()
                .extract();
    }


    protected static ExtractableResponse<Response> requestGetCurrencies(
            final MemberTokens memberTokens,
            final int page,
            final int size
    ) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .when().get("/admin/currencies?page={page}&size={size}", page, size)
                .then().log().all()
                .extract();
    }
}
