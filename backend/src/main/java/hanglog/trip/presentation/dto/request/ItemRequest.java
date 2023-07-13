package hanglog.trip.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequest {

    private final Boolean itemType;
    private final String title;
    private final Double rating;
    private final String memo;
    private final Long dayLogId;
    private final PlaceRequest place;
    private final ExpenseRequest expense;
    // TODO: images 추가 필요
}
