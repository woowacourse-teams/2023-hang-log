package hanglog.trip.dto.response;

import hanglog.expense.dto.response.ItemExpenseResponse;
import hanglog.trip.domain.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemDetailResponse {

    private final Long id;
    private final String title;
    private final int ordinal;
    private final ItemExpenseResponse expense;

    public static ItemDetailResponse of(final Item item) {
        return new ItemDetailResponse(
                item.getId(),
                item.getTitle(),
                item.getOrdinal(),
                ItemExpenseResponse.of(item.getExpense())
        );
    }
}
