package hanglog.trip.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequest {

    @NotNull(message = "여행 아이템의 타입을 입력해주세요.")
    private final Boolean itemType;

    @Size(max = 50, message = "여행 아이템의 제목은 50자를 초과할 수 없습니다.")
    private final String title;

    private final Double rating;

    @Size(max = 255, message = "여행 아이템의 메모는 255자를 초과할 수 없습니다.")
    private final String memo;

    @NotNull(message = "여행 아이템이 속한 데이 로그를 입력해주세요.")
    private final Long dayLogId;

    private final PlaceRequest place;

    private final ExpenseRequest expense;
    // TODO: images 추가 필요
}
