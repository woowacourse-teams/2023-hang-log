package hanglog.trip.integration;

import static hanglog.global.IntegrationFixture.EDINBURGH;
import static hanglog.global.IntegrationFixture.END_DATE;
import static hanglog.global.IntegrationFixture.LONDON;
import static hanglog.global.IntegrationFixture.START_DATE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import hanglog.category.dto.CategoryResponse;
import hanglog.expense.dto.response.ItemExpenseResponse;
import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.ExpenseRequest;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.ItemUpdateRequest;
import hanglog.trip.dto.request.PlaceRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.response.DayLogResponse;
import hanglog.trip.dto.response.ItemResponse;
import hanglog.trip.dto.response.PlaceResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemIntegrationTest extends IntegrationTest {

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
                tripCreateRequest
        );
        tripId = Long.parseLong(parseUri(tripCreateResponse.header("Location")));

        final ExtractableResponse<Response> tripGetResponse = TripIntegrationTest.requestGetTrip(tripId);
        final TripDetailResponse tripDetailResponse = tripGetResponse.as(TripDetailResponse.class);
        dayLogId = tripDetailResponse.getDayLogs().get(0).getId();
    }

    @DisplayName("NonSpot인 Item을 생성한다.")
    @Test
    void createItem_NonSpot() {
        // when
        final ItemRequest itemRequest = new ItemRequest(
                false,
                "non spot",
                null,
                null,
                dayLogId,
                Collections.emptyList(),
                null,
                null
        );

        final ExtractableResponse<Response> response = requestCreateItem(tripId, itemRequest);
        final Long itemId = Long.parseLong(parseUri(response.header("Location")));
        final List<ItemResponse> itemResponses = requestGetItems(tripId, dayLogId);

        final ItemResponse expectedItemResponse = new ItemResponse(
                itemId,
                itemRequest.getItemType(),
                itemRequest.getTitle(),
                1,
                itemRequest.getRating(),
                itemRequest.getMemo(),
                itemRequest.getImageUrls(),
                null,
                null
        );

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                    softly.assertThat(itemResponses.get(0))
                            .usingRecursiveComparison()
                            .isEqualTo(expectedItemResponse);
                }
        );
    }

    @DisplayName("Spot인 Item을 생성한다.")
    @Test
    void createItem_Spot() {
        // when
        final PlaceRequest placeRequest = new PlaceRequest(
                "place name",
                BigDecimal.valueOf(1.1),
                BigDecimal.valueOf(2.2),
                List.of("temp1", "culture", "temp2")
        );

        final ExpenseRequest expenseRequest = new ExpenseRequest(
                "EUR",
                BigDecimal.valueOf(10.5),
                200L
        );

        final ItemRequest itemRequest = new ItemRequest(
                true,
                "spot",
                5.0,
                "memo",
                dayLogId,
                List.of("https://hanglog.com/img/test1.png", "https://hanglog.com/img/test2.png"),
                placeRequest,
                expenseRequest
        );

        final ExtractableResponse<Response> response = requestCreateItem(tripId, itemRequest);
        final Long itemId = Long.parseLong(parseUri(response.header("Location")));
        final List<ItemResponse> itemResponses = requestGetItems(tripId, dayLogId);

        final CategoryResponse expectedCategoryResponse = new CategoryResponse(
                200L,
                "문화"
        );

        final PlaceResponse expectedPlaceResponse = new PlaceResponse(
                1L,
                itemRequest.getPlace().getName(),
                itemRequest.getPlace().getLatitude().setScale(13),
                itemRequest.getPlace().getLongitude().setScale(13),
                expectedCategoryResponse
        );

        final ItemExpenseResponse expectedItemExpenseResponse = new ItemExpenseResponse(
                1L,
                "EUR",
                BigDecimal.valueOf(10.5).setScale(3),
                expectedCategoryResponse
        );

        final ItemResponse expectedItemResponse = new ItemResponse(
                itemId,
                itemRequest.getItemType(),
                itemRequest.getTitle(),
                1,
                itemRequest.getRating(),
                itemRequest.getMemo(),
                itemRequest.getImageUrls(),
                expectedPlaceResponse,
                expectedItemExpenseResponse
        );

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                    softly.assertThat(itemResponses.get(0))
                            .usingRecursiveComparison()
                            .isEqualTo(expectedItemResponse);
                }
        );
    }

    @DisplayName("Item을 수정한다.")
    @Test
    void updateItem() {
        // when
        final ItemRequest itemRequest = getNonSpotItemRequest();
        final ExtractableResponse<Response> createResponse = requestCreateItem(tripId, itemRequest);
        final Long itemId = Long.parseLong(parseUri(createResponse.header("Location")));

        final ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest(
                true,
                "updated item",
                4.5,
                "updated memo",
                dayLogId,
                List.of("https://hanglog.com/img/test1.png", "https://hanglog.com/img/test2.png"),
                false,
                getPlaceRequest(),
                getExpenseRequest()
        );

        final ExtractableResponse<Response> response = requestUpdateItem(tripId, itemId, itemUpdateRequest);
        final List<ItemResponse> itemResponses = requestGetItems(tripId, dayLogId);

        final CategoryResponse expectedCategoryResponse = new CategoryResponse(
                200L,
                "문화"
        );

        final PlaceResponse expectedPlaceResponse = new PlaceResponse(
                1L,
                itemUpdateRequest.getPlace().getName(),
                itemUpdateRequest.getPlace().getLatitude().setScale(13),
                itemUpdateRequest.getPlace().getLongitude().setScale(13),
                expectedCategoryResponse
        );

        final ItemExpenseResponse expectedItemExpenseResponse = new ItemExpenseResponse(
                1L,
                "EUR",
                BigDecimal.valueOf(10.5).setScale(3),
                expectedCategoryResponse
        );

        final ItemResponse expectedItemResponse = new ItemResponse(
                itemId,
                itemUpdateRequest.getItemType(),
                itemUpdateRequest.getTitle(),
                1,
                itemUpdateRequest.getRating(),
                itemUpdateRequest.getMemo(),
                itemUpdateRequest.getImageUrls(),
                expectedPlaceResponse,
                expectedItemExpenseResponse
        );

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
                    softly.assertThat(itemResponses.get(0))
                            .usingRecursiveComparison()
                            .isEqualTo(expectedItemResponse);
                }
        );
    }

    protected static ExtractableResponse<Response> requestCreateItem(final Long tripId, final ItemRequest itemRequest) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(itemRequest)
                .when().post("/trips/{tripId}/items", tripId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestUpdateItem(
            final Long tripId,
            final Long itemId,
            final ItemUpdateRequest itemUpdateRequest
    ) {
        return RestAssured
                .given().log().all()
                .contentType(JSON)
                .body(itemUpdateRequest)
                .when().put("/trips/{tripId}/items/{itemId}", tripId, itemId)
                .then().log().all()
                .extract();
    }

    private List<ItemResponse> requestGetItems(final Long tripId, final Long dayLogId) {
        final DayLogResponse dayLogResponse = RestAssured
                .given().log().all()
                .when().get("/trips/{tripId}/daylogs/{dayLogId}", tripId, dayLogId)
                .then().log().all()
                .extract()
                .as(DayLogResponse.class);

        return dayLogResponse.getItems();
    }


    private ItemRequest getNonSpotItemRequest() {
        return new ItemRequest(
                false,
                "non spot",
                null,
                null,
                dayLogId,
                Collections.emptyList(),
                null,
                null
        );
    }

    private PlaceRequest getPlaceRequest() {
        return new PlaceRequest(
                "place name",
                BigDecimal.valueOf(1.1),
                BigDecimal.valueOf(2.2),
                List.of("temp1", "culture", "temp2")
        );
    }

    private ExpenseRequest getExpenseRequest() {
        return new ExpenseRequest(
                "EUR",
                BigDecimal.valueOf(10.5),
                200L
        );
    }

    private ItemRequest getSpotItemRequest() {
        return new ItemRequest(
                true,
                "spot",
                5.0,
                "memo",
                dayLogId,
                List.of("https://hanglog.com/img/test1.png", "https://hanglog.com/img/test2.png"),
                getPlaceRequest(),
                getExpenseRequest()
        );
    }
}
