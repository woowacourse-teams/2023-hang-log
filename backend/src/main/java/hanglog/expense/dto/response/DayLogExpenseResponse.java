package hanglog.expense.dto.response;

import hanglog.trip.domain.DayLog;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DayLogExpenseResponse {

    private static final int DEFAULT_DAY = 1;

    private final Long id;
    private final Integer ordinal;
    private final LocalDate date;
    private final int totalAmount;
    private final List<ItemDetailResponse> items;

    public static DayLogExpenseResponse of(final DayLog dayLog, final int totalAmount) {
        final List<ItemDetailResponse> itemResponses = dayLog.getItems().stream()
                .map(ItemDetailResponse::of)
                .toList();
        return new DayLogExpenseResponse(
                dayLog.getId(),
                dayLog.getOrdinal(),
                calculateDate(dayLog),
                totalAmount,
                itemResponses
        );
    }

    private static LocalDate calculateDate(final DayLog dayLog) {
        final LocalDate startDate = dayLog.getTrip().getStartDate();
        return startDate.plusDays(dayLog.getOrdinal() - DEFAULT_DAY);
    }
}
