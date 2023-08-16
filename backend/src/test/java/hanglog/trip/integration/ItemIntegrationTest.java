package hanglog.trip.integration;

import static io.restassured.http.ContentType.JSON;

import hanglog.auth.domain.MemberTokens;
import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.ItemRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;

public class ItemIntegrationTest extends IntegrationTest {

    protected static ExtractableResponse<Response> requestCreateItem(final MemberTokens memberTokens, final Long tripId,
                                                                     final ItemRequest itemRequest) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(itemRequest)
                .when().post("/trips/{tripId}/items", tripId)
                .then().log().all()
                .extract();
    }
}
