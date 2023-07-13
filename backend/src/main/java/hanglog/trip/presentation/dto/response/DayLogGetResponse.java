package hanglog.trip.presentation.dto.response;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogGetResponse {

    private final Long id;
    private final String title;
    private final Integer ordinal;
    private final List<Item> items;

    public static DayLogGetResponse of(final DayLog dayLog) {
        return new DayLogGetResponse(
                dayLog.getId(),
                dayLog.getTitle(),
                dayLog.getOrdinal(),
                dayLog.getItems()
        );
    }
}
