package hanglog.expense.dto.response;

import hanglog.trip.domain.DayLog;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogInExpenseResponse {

    private static final int DEFAULT_DAY = 1;

    private final Long id;
    private final Integer ordinal;
    private final LocalDate date;
    private final int totalAmount;
    private final List<ItemInDayLogResponse> items;

    public static List<DayLogInExpenseResponse> of(final Map<DayLog, Integer> dayLogTotalAmounts) {
        return dayLogTotalAmounts.entrySet().stream()
                .map(entry -> getDayLogResponse(entry.getKey(), entry.getValue())).
                toList();
    }

    private static DayLogInExpenseResponse getDayLogResponse(final DayLog dayLog, final int totalAmount) {
        final List<ItemInDayLogResponse> itemResponses = dayLog.getItems().stream()
                .map(ItemInDayLogResponse::of)
                .toList();
        return new DayLogInExpenseResponse(
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
