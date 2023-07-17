package hanglog.trip.presentation.dto.response;

import hanglog.expense.Expense;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemResponse {
    private final Long id;
    private final Boolean itemType;
    private final String title;
    private final Integer ordinal;
    private final Double rating;
    private final String memo;
    private final PlaceResponse place;
    private final ExpenseResponse expense;
    // TODO: imageUrls 추가 필요

    public static ItemResponse of(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getItemType().isSpot(),
                item.getTitle(),
                item.getOrdinal(),
                item.getRating(),
                item.getMemo(),
                getPlaceResponse(item.getPlace()),
                getExpenseResponse(item.getExpense())
        );
    }

    public static PlaceResponse getPlaceResponse(Place place) {
        if (place == null) {
            return null;
        }
        return PlaceResponse.of(place);
    }

    public static ExpenseResponse getExpenseResponse(Expense expense) {
        if (expense == null) {
            return null;
        }
        return ExpenseResponse.of(expense);
    }
}
