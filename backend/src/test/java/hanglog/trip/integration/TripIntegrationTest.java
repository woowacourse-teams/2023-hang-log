package hanglog.trip.integration;

import static hanglog.global.IntegrationFixture.EDINBURGH;
import static hanglog.global.IntegrationFixture.END_DATE;
import static hanglog.global.IntegrationFixture.LONDON;
import static hanglog.global.IntegrationFixture.START_DATE;
import static io.restassured.http.ContentType.JSON;

import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.TripCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;

public class TripIntegrationTest extends IntegrationTest {

    final TripCreateRequest tripCreateRequest = new TripCreateRequest(
            START_DATE,
            END_DATE,
            Arrays.asList(LONDON.getId(), EDINBURGH.getId())
    );

    protected static ExtractableResponse<Response> requestCreateTrip(final TripCreateRequest tripCreateRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(tripCreateRequest)
                .when().post("/trips")
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> requestGetTrips(final Long tripId) {
        return RestAssured
                .given().log().all()
                .when().get("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }
}
