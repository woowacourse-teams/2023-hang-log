package hanglog.trip.dto.request;

import static hanglog.global.exception.ExceptionCode.INVALID_NOT_NULL_PLACE;
import static hanglog.global.exception.ExceptionCode.INVALID_NULL_PLACE;
import static hanglog.global.exception.ExceptionCode.INVALID_RATING;

import hanglog.global.exception.BadRequestException;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class ItemRequest {

    private static final double RATING_DECIMAL_UNIT = 0.5;

    @NotNull(message = "여행 아이템의 타입을 입력해주세요.")
    private final Boolean itemType;

    @NotBlank(message = "여행 아이템의 제목을 입력해주세요.")
    @Size(max = 50, message = "여행 아이템의 제목은 50자를 초과할 수 없습니다.")
    private final String title;

    @DecimalMax(value = "5.0", message = "여행 별점은 5점을 초과할 수 없습니다.")
    private final Double rating;

    @Size(max = 255, message = "여행 아이템의 메모는 255자를 초과할 수 없습니다.")
    private final String memo;

    @NotNull(message = "여행 아이템이 속한 데이 로그를 입력해주세요.")
    private final Long dayLogId;

    @Size(max = 10, message = "여행 아이템의 이미지는 최대 10개까지 첨부할 수 있습니다.")
    private final List<String> imageUrls;

    private final PlaceRequest place;

    private final ExpenseRequest expense;


    public ItemRequest(
            final Boolean itemType,
            final String title,
            final Double rating,
            final String memo,
            final Long dayLogId,
            final List<String> imageUrls,
            final PlaceRequest place, final ExpenseRequest expense
    ) {
        validateExistPlaceWhenSpot(itemType, place);
        validateNoExistPlaceWhenNonSpot(itemType, place);
        validateRatingFormat(rating);
        this.itemType = itemType;
        this.title = title;
        this.rating = rating;
        this.memo = memo;
        this.dayLogId = dayLogId;
        this.imageUrls = imageUrls;
        this.place = place;
        this.expense = expense;
    }

    private void validateExistPlaceWhenSpot(final Boolean itemType, final PlaceRequest place) {
        if (itemType && place == null) {
            throw new BadRequestException(INVALID_NULL_PLACE);
        }
    }

    private void validateNoExistPlaceWhenNonSpot(final Boolean itemType, final PlaceRequest place) {
        if (!itemType && place != null) {
            throw new BadRequestException(INVALID_NOT_NULL_PLACE);
        }
    }

    private void validateRatingFormat(final Double rating) {
        if (rating != null && isInvalidRatingFormat(rating)) {
            throw new BadRequestException(INVALID_RATING);
        }
    }

    private boolean isInvalidRatingFormat(final Double rating) {
        return rating % RATING_DECIMAL_UNIT != 0;
    }
}
