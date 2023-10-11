package hanglog.trip.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemElement {

    private final Long itemId;
    private final Long placeId;
    private final Long expenseId;
}
