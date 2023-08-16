package hanglog.trip.dto.request;

import static hanglog.global.exception.ExceptionCode.INVALID_NOT_NULL_PLACE;
import static hanglog.global.exception.ExceptionCode.INVALID_NULL_PLACE;
import static hanglog.global.exception.ExceptionCode.INVALID_RATING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hanglog.global.exception.BadRequestException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemRequestTest {

    @DisplayName("Spot인 아이템의 장소 정보가 Null이라면 예외가 발생한다.")
    @Test
    void validateExistPlaceWhenSpot() {
        assertThatThrownBy(() -> new ItemRequest(
                true,
                "title",
                1.0,
                "memo",
                1L,
                Collections.emptyList(),
                null,
                null)
        )
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_NULL_PLACE.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_NULL_PLACE.getCode());
    }

    @DisplayName("NonSpot인 아이템의 장소 정보가 Not Null이라면 예외가 발생한다.")
    @Test
    void validateNoExistPlaceWhenNonSpot() {
        final PlaceRequest placeRequest = new PlaceRequest(
                "place name",
                BigDecimal.valueOf(1.1),
                BigDecimal.valueOf(2.2),
                List.of("temp1", "culture", "temp2")
        );

        assertThatThrownBy(() -> new ItemRequest(
                false,
                "title",
                1.0,
                "memo",
                1L,
                Collections.emptyList(),
                placeRequest,
                null)
        )
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_NOT_NULL_PLACE.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_NOT_NULL_PLACE.getCode());
    }

    @DisplayName("별점이 N.5 형태가 아니라면 예외가 발생한다.")
    @Test
    void validateRatingFormat() {
        assertThatThrownBy(() -> new ItemRequest(
                false,
                "title",
                1.3,
                "memo",
                1L,
                Collections.emptyList(),
                null,
                null)
        )
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_RATING.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_RATING.getCode());
    }
}
