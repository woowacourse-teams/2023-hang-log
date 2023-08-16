package hanglog.share.integration;

import static hanglog.global.IntegrationFixture.END_DATE;
import static hanglog.global.IntegrationFixture.START_DATE;
import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.global.IntegrationTest;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.integration.TripIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

class SharedCodeIntegrationTest extends IntegrationTest {

    private Long tripId;

    @BeforeEach
    void setTrip() {
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(START_DATE, END_DATE, List.of(1L));
        tripId = Long.parseLong(TripIntegrationTest.requestCreateTrip(memberTokens, tripCreateRequest)
                .header("location").replace("/trips/", ""));
    }

    private ExtractableResponse<Response> requestUpdateSharedTripStatus(final boolean status) {
        return RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .body(new SharedTripStatusRequest(status))
                .contentType(JSON)
                .when().patch("/trips/{tripId}/share", tripId)
                .then().log().all()
                .extract();
    }

    @DisplayName("공유 상태를 변경한다")
    @Test
    void updateSharedStatus() {
        // when
        final ExtractableResponse<Response> response = requestUpdateSharedTripStatus(true);

        // then
        final Optional<String> sharedCode = Optional.ofNullable(response.body().jsonPath().get("sharedCode"));
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(sharedCode.isPresent()).isTrue();
                }
        );
    }

    @DisplayName("공유된 여행을 조회한다")
    @Test
    void getSharedTrip() {
        // given
        final String sharedCode = requestUpdateSharedTripStatus(true).body().jsonPath().get("sharedCode");

        // when
        final ExtractableResponse<Response> response = RestAssured.given()
                .when().get("/shared-trips/{sharedCode}", sharedCode)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("비공유된 여행은 조회할 수 없다")
    @Test
    void getSharedTrip_UnsharedFail() {
        // given
        final String sharedCode = requestUpdateSharedTripStatus(true).body().jsonPath().get("sharedCode");
        requestUpdateSharedTripStatus(false);

        // when
        final ExtractableResponse<Response> response = RestAssured.given()
                .when().get("/shared-trips/{sharedCode}", sharedCode)
                .then().log().all()
                .extract();
        final Integer errorCode = Integer.parseInt(response.body().jsonPath().get("code").toString());
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softly.assertThat(errorCode).isEqualTo(INVALID_SHARE_CODE.getCode());
                }
        );
    }
}
