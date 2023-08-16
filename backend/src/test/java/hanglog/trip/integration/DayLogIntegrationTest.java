package hanglog.trip.integration;

import static hanglog.global.IntegrationFixture.START_DATE;
import static hanglog.global.IntegrationFixture.TRIP_CREATE_REQUEST;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogResponse;
import hanglog.trip.dto.response.ItemResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DayLogIntegrationTest extends IntegrationTest {

    private Long tripId;
    private Long dayLogId;

    @BeforeEach
    void setUp() {
        final ExtractableResponse<Response> tripCreateResponse = TripIntegrationTest.requestCreateTrip(memberTokens,
                TRIP_CREATE_REQUEST);
        tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        final ExtractableResponse<Response> tripGetResponse = TripIntegrationTest.requestGetTrip(memberTokens, tripId);
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
        final ExtractableResponse<Response> response = requestUpdateDayLogTitle(tripId, dayLogId, request);
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

    private Long createMockItem(final Long tripId, final Long dayLogId) {
        final ItemRequest itemRequest = new ItemRequest(
                false,
                "title",
                null,
                null,
                dayLogId,
                List.of(),
                null,
                null
        );

        final ExtractableResponse<Response> itemCreateResponse = ItemIntegrationTest.requestCreateItem(
                memberTokens,
                tripId,
                itemRequest
        );
        return Long.parseLong(parseUri(itemCreateResponse.header("Location")));
    }

    @DisplayName("데이로그 아이템 순서를 업데이트한다")
    @Test
    void updateOrdinalOfItems() {
        // given
        final Long itemId1 = createMockItem(tripId, dayLogId);
        final Long itemId2 = createMockItem(tripId, dayLogId);
        final Long itemId3 = createMockItem(tripId, dayLogId);
        final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest = new ItemsOrdinalUpdateRequest(
                List.of(itemId2, itemId3, itemId1)
        );

        // when
        final ExtractableResponse<Response> response = requestUpdateOrdinalOfItems(
                tripId,
                dayLogId,
                itemsOrdinalUpdateRequest
        );
        final ExtractableResponse<Response> dayLogGetResponse = requestGetDayLog(tripId, dayLogId);
        final DayLogResponse dayLogResponse = dayLogGetResponse.as(DayLogResponse.class);
        final List<ItemResponse> itemResponses = dayLogResponse.getItems();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
                    softly.assertThat(itemResponses.get(0).getOrdinal()).isEqualTo(1);
                    softly.assertThat(itemResponses.get(0).getId()).isEqualTo(itemId2);
                    softly.assertThat(itemResponses.get(1).getOrdinal()).isEqualTo(2);
                    softly.assertThat(itemResponses.get(1).getId()).isEqualTo(itemId3);
                    softly.assertThat(itemResponses.get(2).getOrdinal()).isEqualTo(3);
                    softly.assertThat(itemResponses.get(2).getId()).isEqualTo(itemId1);
                }
        );
    }


    private ExtractableResponse<Response> requestGetDayLog(final Long tripId, final Long dayLogId) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .when().get("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateDayLogTitle(
            final Long tripId,
            final Long dayLogId,
            final DayLogUpdateTitleRequest request
    ) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(request)
                .when().patch("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateOrdinalOfItems(
            final Long tripId,
            final Long dayLogId,
            final ItemsOrdinalUpdateRequest request
    ) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(request)
                .when().patch("/trips/{tripId}/daylogs/{dayLogId}/order", tripId, dayLogId)
                .then().log().all()
                .extract();
    }
}
