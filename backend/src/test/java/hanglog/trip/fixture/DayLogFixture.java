package hanglog.trip.fixture;

import hanglog.trip.domain.DayLog;
import java.util.List;

public final class DayLogFixture {

    public static final DayLog LONDON_DAYLOG_1 = new DayLog(
            1L,
            "런던 여행 1일차",
            1,
            TripFixture.LONDON_TRIP,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_2 = new DayLog(
            2L,
            "런던 여행 2일차",
            2,
            TripFixture.LONDON_TRIP,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_EXTRA = new DayLog(
            3L,
            "경비 페이지",
            3,
            TripFixture.LONDON_TRIP,
            List.of()
    );

    public static final DayLog UPDATED_LONDON_DAYLOG = new DayLog(
            1L,
            "수정된 런던 여행",
            1,
            TripFixture.LONDON_TRIP,
            List.of()
    );
}
