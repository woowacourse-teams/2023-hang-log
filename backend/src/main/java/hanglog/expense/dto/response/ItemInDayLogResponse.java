package hanglog.expense.dto.response;

import hanglog.trip.domain.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemInDayLogResponse {

    private final long id;
    private final String title;
    private final int ordinal;
    private final ExpenseInItemResponse expense;

    public static ItemInDayLogResponse of(final Item item) {
        return new ItemInDayLogResponse(
                item.getId(),
                item.getTitle(),
                item.getOrdinal(),
                ExpenseInItemResponse.of(item.getExpense())
        );
    }
}
