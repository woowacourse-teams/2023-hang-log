package hanglog.trip.integration;

import static hanglog.IntegrationFixture.EDINBURGH;
import static hanglog.IntegrationFixture.END_DATE;
import static hanglog.IntegrationFixture.LAHGON_TRIP;
import static hanglog.IntegrationFixture.LONDON;
import static hanglog.IntegrationFixture.PARIS;
import static hanglog.IntegrationFixture.START_DATE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.IntegrationTest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TripIntegrationTest extends IntegrationTest {

    final TripCreateRequest tripCreateRequest = new TripCreateRequest(
            START_DATE,
            END_DATE,
            Arrays.asList(LONDON.getId(), EDINBURGH.getId())
    );

    @DisplayName("Trip을 생성한다.")
    @Test
    void createTrip() {
        // when
        final ExtractableResponse<Response> response = requestCreateTrip(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(response.header("Location")));

        final TripDetailResponse tripDetailResponse = requestGetTrip(tripId).as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .ignoringFields("id", "cities", "dayLogs")
                            .isEqualTo(TripDetailResponse.of(LAHGON_TRIP, List.of(LONDON, EDINBURGH)));
                    softly.assertThat(tripDetailResponse.getCities())
                            .usingRecursiveComparison()
                            .ignoringFieldsOfTypes(BigDecimal.class)
                            .isEqualTo(List.of(LONDON, EDINBURGH));
                    softly.assertThat(tripDetailResponse.getDayLogs().size()).isEqualTo(4);
                }
        );
    }

    @DisplayName("하나의 Trip을 조회한다.")
    @Test
    void getTrip() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));
        final TripDetailResponse expected = TripDetailResponse.of(LAHGON_TRIP, List.of(LONDON, EDINBURGH));

        // when
        final ExtractableResponse<Response> response = requestGetTrip(tripId);
        final TripDetailResponse tripDetailResponse = response.as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .ignoringFields("id", "cities", "dayLogs")
                            .isEqualTo(expected);
                    softly.assertThat(tripDetailResponse.getCities())
                            .usingRecursiveComparison()
                            .ignoringFieldsOfTypes(BigDecimal.class)
                            .isEqualTo(expected.getCities());
                    softly.assertThat(tripDetailResponse.getDayLogs().size()).isEqualTo(4);
                }
        );
    }

    @DisplayName("Trip을 수정한다. (여행 기간 감소)")
    @Test
    void updateTrip_DecreasePeriod() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        final TripUpdateRequest tripUpdateRequest = new TripUpdateRequest(
                "수정된 여행 제목",
                null,
                LocalDate.of(2023, 8, 1),
                LocalDate.of(2023, 8, 2),
                "매번 색다르고 즐거운 서유럽 여행",
                List.of(1L, 2L, 3L)
        );

        // when
        final ExtractableResponse<Response> response = requestUpdateTrip(tripId, tripUpdateRequest);
        final TripDetailResponse tripDetailResponse = requestGetTrip(tripId).as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .comparingOnlyFields("title", "startDate", "endDate", "description")
                            .isEqualTo(tripUpdateRequest);
                    softly.assertThat(tripDetailResponse.getCities())
                            .usingRecursiveComparison()
                            .ignoringFieldsOfTypes(BigDecimal.class)
                            .isEqualTo(List.of(LONDON, EDINBURGH, PARIS));
                    softly.assertThat(tripDetailResponse.getDayLogs().size()).isEqualTo(3);
                }
        );
    }

    @DisplayName("Trip을 수정한다. (여행 기간 증가)")
    @Test
    void updateTrip_IncreasePeriod() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        final TripUpdateRequest tripUpdateRequest = new TripUpdateRequest(
                "수정된 여행 제목",
                null,
                LocalDate.of(2023, 8, 1),
                LocalDate.of(2023, 8, 5),
                "매번 색다르고 즐거운 서유럽 여행",
                List.of(1L, 2L, 3L)
        );

        // when
        final ExtractableResponse<Response> response = requestUpdateTrip(tripId, tripUpdateRequest);
        final TripDetailResponse tripDetailResponse = requestGetTrip(tripId).as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .comparingOnlyFields("title", "startDate", "endDate", "description")
                            .isEqualTo(tripUpdateRequest);
                    softly.assertThat(tripDetailResponse.getCities())
                            .usingRecursiveComparison()
                            .ignoringFieldsOfTypes(BigDecimal.class)
                            .isEqualTo(List.of(LONDON, EDINBURGH, PARIS));
                    softly.assertThat(tripDetailResponse.getDayLogs().size()).isEqualTo(6);
                }
        );
    }

    @DisplayName("하나의 Trip을 삭제한다.")
    @Test
    void deleteTrip() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        // when
        final ExtractableResponse<Response> response = requestDeleteTrip(tripId);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(requestGetTrip(tripId).statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                }
        );
    }

    protected static ExtractableResponse<Response> requestCreateTrip(final TripCreateRequest tripCreateRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(tripCreateRequest)
                .when().post("/trips")
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> requestGetTrip(final Long tripId) {
        return RestAssured
                .given().log().all()
                .when().get("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateTrip(final Long tripId, final TripUpdateRequest tripUpdateRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(tripUpdateRequest)
                .when().put("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestDeleteTrip(final Long tripId) {
        return RestAssured
                .given().log().all()
                .when().delete("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }
}
