package hanglog.integration.controller;

import static hanglog.integration.IntegrationFixture.EDINBURGH;
import static hanglog.integration.IntegrationFixture.LAHGON_TRIP;
import static hanglog.integration.IntegrationFixture.LONDON;
import static hanglog.integration.IntegrationFixture.TRIP_CREATE_REQUEST;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import hanglog.login.domain.MemberTokens;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TripIntegrationTest extends IntegrationTest {

    @DisplayName("Trip을 생성한다.")
    @Test
    void createTrip() {
        // when
        final ExtractableResponse<Response> response = requestCreateTrip(memberTokens, TRIP_CREATE_REQUEST);
        final Long tripId = Long.parseLong(parseUri(response.header("Location")));

        final TripDetailResponse tripDetailResponse = requestGetTrip(memberTokens, tripId).as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .ignoringFields("id", "dayLogs", "sharedCode")
                            .ignoringFieldsMatchingRegexes(".*latitude", ".*longitude")
                            .isEqualTo(TripDetailResponse.personalTrip(LAHGON_TRIP, List.of(LONDON, EDINBURGH)));
                    softly.assertThat(tripDetailResponse.getDayLogs().size()).isEqualTo(4);
                }
        );
    }

    @DisplayName("하나의 Trip을 조회한다.")
    @Test
    void getTrip() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(memberTokens, TRIP_CREATE_REQUEST);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));
        final TripDetailResponse expected = TripDetailResponse.personalTrip(LAHGON_TRIP, List.of(LONDON, EDINBURGH));

        // when
        final ExtractableResponse<Response> response = requestGetTrip(memberTokens, tripId);
        final TripDetailResponse tripDetailResponse = response.as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .ignoringFields("id", "dayLogs", "sharedCode")
                            .ignoringFieldsMatchingRegexes(".*latitude", ".*longitude")
                            .isEqualTo(expected);
                    softly.assertThat(tripDetailResponse.getDayLogs().size()).isEqualTo(4);
                }
        );
    }

    @DisplayName("Trip을 수정한다. (여행 기간 감소)")
    @Test
    void updateTrip_DecreasePeriod() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(memberTokens, TRIP_CREATE_REQUEST);
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
        final ExtractableResponse<Response> response = requestUpdateTrip(memberTokens, tripId, tripUpdateRequest);
        final TripDetailResponse tripDetailResponse = requestGetTrip(memberTokens, tripId).as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .comparingOnlyFields("title", "startDate", "endDate", "description", "cities")
                            .ignoringFieldsMatchingRegexes(".*latitude", ".*longitude")
                            .isEqualTo(tripUpdateRequest);
                    softly.assertThat(tripDetailResponse.getDayLogs())
                            .size()
                            .isEqualTo(3);
                    softly.assertThat(tripDetailResponse.getDayLogs())
                            .extracting("ordinal")
                            .containsExactly(1, 2, 3);
                }
        );
    }

    @DisplayName("Trip을 수정한다. (여행 기간 증가)")
    @Test
    void updateTrip_IncreasePeriod() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(memberTokens, TRIP_CREATE_REQUEST);
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
        final ExtractableResponse<Response> response = requestUpdateTrip(memberTokens, tripId, tripUpdateRequest);
        final TripDetailResponse tripDetailResponse = requestGetTrip(memberTokens, tripId).as(TripDetailResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .comparingOnlyFields("title", "startDate", "endDate", "description", "cities")
                            .ignoringFieldsMatchingRegexes(".*latitude", ".*longitude")
                            .isEqualTo(tripUpdateRequest);
                    softly.assertThat(tripDetailResponse.getDayLogs())
                            .size()
                            .isEqualTo(6);
                    softly.assertThat(tripDetailResponse.getDayLogs())
                            .extracting("ordinal")
                            .containsExactly(1, 2, 3, 4, 5, 6);
                }
        );
    }

    @DisplayName("하나의 Trip을 삭제한다.")
    @Test
    void deleteTrip() {
        // given
        final ExtractableResponse<Response> tripCreateResponse = requestCreateTrip(memberTokens, TRIP_CREATE_REQUEST);
        final Long tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        // when
        final ExtractableResponse<Response> response = requestDeleteTrip(memberTokens, tripId);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(requestGetTrip(memberTokens, tripId).statusCode())
                            .isEqualTo(HttpStatus.BAD_REQUEST.value());
                }
        );
    }

    public static ExtractableResponse<Response> requestCreateTrip(final MemberTokens memberTokens,
                                                                  final TripCreateRequest tripCreateRequest) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(tripCreateRequest)
                .when().post("/trips")
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> requestGetTrip(final MemberTokens memberTokens, final Long tripId) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .when().get("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateTrip(
            final MemberTokens memberTokens,
            final Long tripId,
            final TripUpdateRequest tripUpdateRequest
    ) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(tripUpdateRequest)
                .when().put("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestDeleteTrip(final MemberTokens memberTokens, final Long tripId) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .when().delete("/trips/{tripId}", tripId)
                .then().log().all()
                .extract();
    }
}
