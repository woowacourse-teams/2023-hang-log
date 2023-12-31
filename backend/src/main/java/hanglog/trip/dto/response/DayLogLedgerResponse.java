package hanglog.trip.dto.response;

import hanglog.trip.domain.DayLogExpense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogLedgerResponse {

    private final Long id;
    private final Integer ordinal;
    private final LocalDate date;
    private final BigDecimal totalAmount;
    private final List<ItemDetailResponse> items;

    public static DayLogLedgerResponse of(final DayLogExpense dayLogExpense) {
        final List<ItemDetailResponse> itemResponses = dayLogExpense.getDayLog().getItems().stream()
                .filter(dayLog -> dayLog.getExpense() != null)
                .map(ItemDetailResponse::of)
                .toList();

        return new DayLogLedgerResponse(
                dayLogExpense.getDayLog().getId(),
                dayLogExpense.getDayLog().getOrdinal(),
                dayLogExpense.getDayLog().calculateDate(),
                dayLogExpense.getAmount().getValue(),
                itemResponses
        );
    }
}
