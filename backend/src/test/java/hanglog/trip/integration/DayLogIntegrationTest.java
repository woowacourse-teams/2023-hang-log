package hanglog.trip.integration;

import static hanglog.global.IntegrationFixture.EDINBURGH;
import static hanglog.global.IntegrationFixture.END_DATE;
import static hanglog.global.IntegrationFixture.LONDON;
import static hanglog.global.IntegrationFixture.START_DATE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.response.DayLogResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DayLogIntegrationTest extends IntegrationTest {

    private Long tripId;
    private Long dayLogId;

    @BeforeEach
    void setUp() {
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                START_DATE,
                END_DATE,
                Arrays.asList(LONDON.getId(), EDINBURGH.getId())
        );

        final ExtractableResponse<Response> tripCreateResponse = TripIntegrationTest.requestCreateTrip(
                tripCreateRequest);
        tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        final ExtractableResponse<Response> tripGetResponse = TripIntegrationTest.requestGetTrips(tripId);
        final TripDetailResponse tripDetailResponse = tripGetResponse.as(TripDetailResponse.class);
        dayLogId = tripDetailResponse.getDayLogs().get(0).getId();
    }

    @DisplayName("데이로그를 조회한다.")
    @Test
    void getDayLog() {
        // given
        final DayLogResponse expected = new DayLogResponse(
                dayLogId,
                "",
                1,
                START_DATE,
                new ArrayList<>()
        );

        // when
        final ExtractableResponse<Response> response = requestGetDayLog(tripId, dayLogId);
        final DayLogResponse result = response.as(DayLogResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(result).usingRecursiveComparison()
                            .isEqualTo(expected);
                }
        );
    }

    @DisplayName("데이로그 제목을 업데이트한다")
    @Test
    void updateDayLogTitle() {
        // given
        final String updatedTitle = "수정된 제목";
        final DayLogUpdateTitleRequest request = new DayLogUpdateTitleRequest(updatedTitle);

        // when
        final ExtractableResponse<Response> response = requestUpdateDayLogTitle(request);
        final ExtractableResponse<Response> dayLogGetResponse = requestGetDayLog(tripId, dayLogId);
        final DayLogResponse dayLogResponse = dayLogGetResponse.as(DayLogResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(dayLogResponse.getTitle()).isEqualTo(updatedTitle);
                }
        );
    }

    private ExtractableResponse<Response> requestGetDayLog(final Long tripId, final Long dayLogId) {
        return RestAssured
                .given().log().all()
                .when().get("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateDayLogTitle(final DayLogUpdateTitleRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().patch("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateOrdinalOfItems(final ItemsOrdinalUpdateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().patch("/trips/{tripId}/daylogs/{dayLogId}/order", tripId, dayLogId)
                .then().log().all()
                .extract();
    }
}
