package hanglog.trip.dto.response;

import hanglog.trip.domain.DayLog;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogGetResponse {

    private static final int DEFAULT_DAY = 1;

    private final Long id;
    private final String title;
    private final Integer ordinal;
    private final LocalDate date;
    private final List<ItemResponse> items;

    public static DayLogGetResponse of(final DayLog dayLog) {
        final LocalDate date = calculateDate(dayLog);
        final List<ItemResponse> itemResponses = dayLog.getItems()
                .stream()
                .map(ItemResponse::of)
                .toList();

        return new DayLogGetResponse(
                dayLog.getId(),
                dayLog.getTitle(),
                dayLog.getOrdinal(),
                date,
                itemResponses
        );
    }

    private static LocalDate calculateDate(final DayLog dayLog) {
        final LocalDate startDate = dayLog.getTrip().getStartDate();
        return startDate.plusDays(dayLog.getOrdinal() - DEFAULT_DAY);
    }
}
