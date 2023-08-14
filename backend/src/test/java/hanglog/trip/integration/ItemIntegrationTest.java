package hanglog.trip.integration;

import static io.restassured.http.ContentType.JSON;

import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.ItemRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ItemIntegrationTest extends IntegrationTest {

    protected static ExtractableResponse<Response> requestCreateItem(final Long tripId, final ItemRequest itemRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(itemRequest)
                .when().post("/trips/{tripId}/items", tripId)
                .then().log().all()
                .extract();
    }
}
