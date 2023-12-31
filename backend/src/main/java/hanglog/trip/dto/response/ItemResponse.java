package hanglog.trip.dto.response;

import hanglog.expense.domain.Expense;
import hanglog.expense.dto.response.ItemExpenseResponse;
import hanglog.trip.domain.Image;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import java.util.List;
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
    private final List<String> imageNames;
    private final PlaceResponse place;
    private final ItemExpenseResponse expense;

    public static ItemResponse of(final Item item) {
        return new ItemResponse(
                item.getId(),
                item.getItemType().isSpot(),
                item.getTitle(),
                item.getOrdinal(),
                item.getRating(),
                item.getMemo(),
                getImageNames(item.getImages()),
                getPlaceResponse(item.getPlace()),
                getExpenseResponse(item.getExpense())
        );
    }

    private static PlaceResponse getPlaceResponse(final Place place) {
        if (place == null) {
            return null;
        }
        return PlaceResponse.of(place);
    }

    private static ItemExpenseResponse getExpenseResponse(final Expense expense) {
        if (expense == null) {
            return null;
        }
        return ItemExpenseResponse.of(expense);
    }

    private static List<String> getImageNames(final List<Image> images) {
        return images.stream()
                .map(Image::getName)
                .toList();
    }
}
