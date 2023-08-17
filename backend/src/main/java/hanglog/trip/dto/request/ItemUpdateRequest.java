package hanglog.trip.dto.request;

import static hanglog.global.exception.ExceptionCode.INVALID_IS_PLACE_UPDATED_WHEN_NON_SPOT;
import static hanglog.global.exception.ExceptionCode.INVALID_NOT_NULL_PLACE;
import static hanglog.global.exception.ExceptionCode.INVALID_NULL_PLACE;
import static hanglog.global.exception.ExceptionCode.INVALID_RATING;

import hanglog.global.exception.BadRequestException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class ItemUpdateRequest {

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

    @NotNull(message = "장소의 업데이트 여부를 입력해 주세요.")
    private final Boolean isPlaceUpdated;

    @Valid
    private final PlaceRequest place;

    @Valid
    private final ExpenseRequest expense;

    public ItemUpdateRequest(
            final Boolean itemType,
            final String title,
            final Double rating,
            final String memo,
            final Long dayLogId,
            final List<String> imageUrls,
            final Boolean isPlaceUpdated,
            final PlaceRequest place,
            final ExpenseRequest expense
    ) {
        validatePlaceWhenSpotAndPlaceUpdated(itemType, place, isPlaceUpdated);
        validatePlaceWhenNonSpot(itemType, place);
        validateIsPlaceUpdatedWhenNonSpot(itemType, isPlaceUpdated);
        validateRatingFormat(rating);
        this.itemType = itemType;
        this.title = title;
        this.rating = rating;
        this.memo = memo;
        this.dayLogId = dayLogId;
        this.imageUrls = imageUrls;
        this.isPlaceUpdated = isPlaceUpdated;
        this.place = place;
        this.expense = expense;
    }

    private void validatePlaceWhenSpotAndPlaceUpdated(
            final Boolean itemType,
            final PlaceRequest place,
            final Boolean isPlaceUpdated
    ) {
        if (itemType && isPlaceUpdated && place == null) {
            throw new BadRequestException(INVALID_NULL_PLACE);
        }
    }

    private void validatePlaceWhenNonSpot(final Boolean itemType, final PlaceRequest place) {
        if (!itemType && place != null) {
            throw new BadRequestException(INVALID_NOT_NULL_PLACE);
        }
    }

    private void validateIsPlaceUpdatedWhenNonSpot(final Boolean itemType, final Boolean isPlaceUpdated) {
        if (!itemType && isPlaceUpdated) {
            throw new BadRequestException(INVALID_IS_PLACE_UPDATED_WHEN_NON_SPOT);
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
