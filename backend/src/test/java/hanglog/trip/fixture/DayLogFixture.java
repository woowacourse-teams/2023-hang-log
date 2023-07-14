package hanglog.trip.fixture;

import hanglog.trip.domain.DayLog;
import java.util.List;

public final class DayLogFixture {

    public static final DayLog LONDON_DAYLOG = new DayLog(
            1L,
            "런던 여행",
            1,
            TripFixture.LONDON_TRIP,
            List.of());

    public static final DayLog UPDATED_LONDON_DAYLOG = new DayLog(
            1L,
            "수정된 런던 여행",
            1,
            TripFixture.LONDON_TRIP,
            List.of());
}
