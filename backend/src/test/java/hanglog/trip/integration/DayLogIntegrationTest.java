package hanglog.trip.integration;

import static hanglog.IntegrationFixture.EDINBURGH;
import static hanglog.IntegrationFixture.END_DATE;
import static hanglog.IntegrationFixture.LONDON;
import static hanglog.IntegrationFixture.START_DATE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.IntegrationTest;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.response.DayLogResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
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

        final ExtractableResponse<Response> tripCreateResponse = TripIntegrationTest.여행_생성_요청(tripCreateRequest);
        tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        final ExtractableResponse<Response> tripGetResponse = TripIntegrationTest.여행_조회_요청(tripId);
        final TripDetailResponse tripDetailResponse = tripGetResponse.as(TripDetailResponse.class);
        dayLogId = tripDetailResponse.getDayLogs().get(0).getId();
    }

    @DisplayName("데이로그를 조회한다.")
    @Test
    void getDayLog() {
        // given
        final DayLogResponse expected = new DayLogResponse(
                1L,
                "",
                1,
                LocalDate.of(2023, 8, 1),
                new ArrayList<>()
        );

        // when
        final ExtractableResponse<Response> response = 데이로그_조회_요청(tripId, dayLogId);
        final DayLogResponse result = response.as(DayLogResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(result).usingRecursiveComparison()
                            .ignoringFields("id")
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
        final ExtractableResponse<Response> response = 데이로그_제목_수정_요청(request);
        final ExtractableResponse<Response> dayLogGetResponse = 데이로그_조회_요청(tripId, dayLogId);
        final DayLogResponse dayLogResponse = dayLogGetResponse.as(DayLogResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(dayLogResponse.getTitle()).isEqualTo(updatedTitle);
                }
        );
    }

    /*
    @DisplayName("데이로그 아이템 순서를 업데이트한다")
    @Test
    void updateOrdinalOfItems() {
        // given
        // TODO: Item n개 요청 후 ID 받아서 request에 넣기

        ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest = new ItemsOrdinalUpdateRequest(List.of(2L, 3L, 1L));

        // when
        final ExtractableResponse<Response> response = 데이로그_아이템_순서_수정_요청(itemsOrdinalUpdateRequest);
        final ExtractableResponse<Response> dayLogGetResponse = 데이로그_조회_요청(tripId, dayLogId);
        final DayLogResponse dayLogResponse = dayLogGetResponse.as(DayLogResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    // TODO: 아이템 순서 맞는지 검증
                    softly.assertThat(dayLogResponse.getItems()).isEmpty();
                }
        );
    }
    */

    private ExtractableResponse<Response> 데이로그_조회_요청(final Long tripId, final Long dayLogId) {
        return RestAssured
                .given().log().all()
                .when().get("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 데이로그_제목_수정_요청(final DayLogUpdateTitleRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().patch("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 데이로그_아이템_순서_수정_요청(final ItemsOrdinalUpdateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(request)
                .when().patch("/trips/{tripId}/daylogs/{dayLogId}/order", tripId, dayLogId)
                .then().log().all()
                .extract();
    }
}
