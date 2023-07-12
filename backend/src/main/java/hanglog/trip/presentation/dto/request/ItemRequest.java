package hanglog.trip.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequest {

    private Boolean itemType;
    private String title;
    private Integer dayLogOrdinal;
    private Double rating;
    private String memo;
    private PlaceRequest place;
    private ExpenseRequest expense;
    // TODO: images 추가 필요
}
