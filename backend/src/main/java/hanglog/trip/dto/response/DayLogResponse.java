package hanglog.trip.dto.response;

import hanglog.trip.domain.DayLog;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogResponse {

    private final Long id;
    private final String title;
    private final Integer ordinal;
    private final LocalDate date;
    private final List<ItemResponse> items;

    public static DayLogResponse of(final DayLog dayLog) {
        final List<ItemResponse> itemResponses = dayLog.getItems().stream()
                .map(ItemResponse::of)
                .toList();

        return new DayLogResponse(
                dayLog.getId(),
                dayLog.getTitle(),
                dayLog.getOrdinal(),
                dayLog.calculateDate(),
                itemResponses
        );
    }
}
