package hanglog.trip.presentation.dto.response;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogGetResponse {

    public static final int DEFAULT_DAY = 1;
    private final Long id;
    private final String title;
    private final Integer ordinal;
    private final LocalDate date;
    private final List<Item> items;

    public static DayLogGetResponse of(final DayLog dayLog) {
        final LocalDate date = calculateDate(dayLog);

        return new DayLogGetResponse(
                dayLog.getId(),
                dayLog.getTitle(),
                dayLog.getOrdinal(),
                date,
                dayLog.getItems()
        );
    }

    private static LocalDate calculateDate(final DayLog dayLog) {
        final LocalDate startDate = dayLog.getTrip().getStartDate();
        return startDate.plusDays(dayLog.getOrdinal() - DEFAULT_DAY);
    }
}
