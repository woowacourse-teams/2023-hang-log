package hanglog.trip.dto.request;

import static hanglog.global.exception.ExceptionCode.INVALID_RATING;

import hanglog.global.exception.BadRequestException;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class ItemUpdateRequest {

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

    private final Boolean isPlaceUpdated;

    private final PlaceRequest place;

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

    private void validateRatingFormat(final Double value) {
        if (value % 0.5 != 0) {
            throw new BadRequestException(INVALID_RATING);
        }
    }
}
