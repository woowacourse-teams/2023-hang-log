package hanglog.trip.integration;

import static hanglog.trip.fixture.IntegrationFixture.EDINBURGH;
import static hanglog.trip.fixture.IntegrationFixture.LAHGON_TRIP;
import static hanglog.trip.fixture.IntegrationFixture.LONDON;
import static hanglog.trip.fixture.IntegrationFixture.PARIS;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.global.IntegrationTest;
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
            LocalDate.of(2023, 8, 1),
            LocalDate.of(2023, 8, 3),
            Arrays.asList(1L, 2L)
    );

    @DisplayName("Trip을 생성한다.")
    @Test
    void createTrip() {
        // when
        final ExtractableResponse<Response> response = 여행_생성_요청(tripCreateRequest);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                }
        );
    }

    @DisplayName("하나의 Trip을 조회한다.")
    @Test
    void getTrip() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = 여행_생성_요청(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));
        final TripDetailResponse expected = TripDetailResponse.of(LAHGON_TRIP, List.of(LONDON, EDINBURGH));

        // when
        final ExtractableResponse<Response> response = 여행_조회_요청(tripId);
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
        final ExtractableResponse<Response> tripCreateResponse = 여행_생성_요청(tripCreateRequest);
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
        final ExtractableResponse<Response> response = 여행_수정_요청(tripId, tripUpdateRequest);

        final ExtractableResponse<Response> tripGetResponse = 여행_조회_요청(tripId);
        final TripDetailResponse tripDetailResponse = tripGetResponse.as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(tripGetResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
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
        final ExtractableResponse<Response> tripCreateResponse = 여행_생성_요청(tripCreateRequest);
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
        final ExtractableResponse<Response> response = 여행_수정_요청(tripId, tripUpdateRequest);

        final ExtractableResponse<Response> tripGetResponse = 여행_조회_요청(tripId);
        final TripDetailResponse tripDetailResponse = tripGetResponse.as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(tripGetResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
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
        final ExtractableResponse<Response> tripCreateResponse = 여행_생성_요청(tripCreateRequest);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        // when
        final ExtractableResponse<Response> response = 여행_삭제_요청(tripId);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(여행_조회_요청(tripId).statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                }
        );
    }

    private ExtractableResponse<Response> 여행_생성_요청(final TripCreateRequest tripCreateRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(tripCreateRequest)
                .when().post("/trips")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 여행_조회_요청(final Long tripId) {
        return RestAssured
                .given().log().all()
                .when().get("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 여행_수정_요청(final Long tripId, final TripUpdateRequest tripUpdateRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(tripUpdateRequest)
                .when().put("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 여행_삭제_요청(final Long tripId) {
        return RestAssured
                .given().log().all()
                .when().delete("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }
}
