package hanglog.trip.integration;

import static hanglog.trip.fixture.IntegrationFixture.DAY_LOG_1;
import static hanglog.trip.fixture.IntegrationFixture.ITEM_1;
import static hanglog.trip.fixture.IntegrationFixture.ITEM_2;
import static hanglog.trip.fixture.IntegrationFixture.ITEM_3;
import static org.assertj.core.api.Assertions.assertThat;

import hanglog.global.IntegrationTest;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogGetResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DayLogIntegrationTest extends IntegrationTest {

    @DisplayName("데이로그를 조회한다.")
    @Test
    void getDayLog() {
        // given
        final DayLogGetResponse expected = new DayLogGetResponse(
                DAY_LOG_1.getId(),
                DAY_LOG_1.getTitle(),
                DAY_LOG_1.getOrdinal(),
                DAY_LOG_1.getDate(),
                DAY_LOG_1.getItems()
        );

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/trips/1/daylogs/1")
                .then().log().all()
                .extract();
        final DayLogGetResponse result = response.as(DayLogGetResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @DisplayName("데이로그 제목을 업데이트한다")
    @Test
    void updateDayLogTitle() {
        // given
        final String updatedTitle = "바꾼 제목";
        final DayLogUpdateTitleRequest request = new DayLogUpdateTitleRequest(updatedTitle);
        final DayLogGetResponse expected = new DayLogGetResponse(
                DAY_LOG_1.getId(),
                updatedTitle,
                DAY_LOG_1.getOrdinal(),
                DAY_LOG_1.getDate(),
                DAY_LOG_1.getItems()
        );

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/trips/1/daylogs/1")
                .then().log().all()
                .extract();
        final DayLogGetResponse result = RestAssured.given().log().all()
                .when().get("/trips/1/daylogs/1")
                .then().log().all()
                .extract().as(DayLogGetResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @DisplayName("데이로그의_아이템_순서를_업데이트한다")
    @Test
    void updateOrdinalOfItems() {
        // given
        final ItemsOrdinalUpdateRequest request = new ItemsOrdinalUpdateRequest(List.of(2L, 3L, 1L));
        final DayLogGetResponse expected = new DayLogGetResponse(
                DAY_LOG_1.getId(),
                DAY_LOG_1.getTitle(),
                DAY_LOG_1.getOrdinal(),
                DAY_LOG_1.getDate(),
                List.of(ITEM_2, ITEM_3, ITEM_1)
        );

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/trips/1/daylogs/1/order")
                .then().log().all()
                .extract();
        final DayLogGetResponse result = RestAssured.given().log().all()
                .when().get("/trips/1/daylogs/1")
                .then().log().all()
                .extract().as(DayLogGetResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(result).usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*ordinal")
                .isEqualTo(expected);
    }
}
