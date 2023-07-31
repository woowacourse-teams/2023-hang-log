package hanglog.trip.fixture;

import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_1;
import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_2;
import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_EXTRA;

import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TripFixture {

    public static final Trip LONDON_TRIP = new Trip(
            1L,
            "런던 여행",
            "https://github.com/woowacourse-teams/2023-hang-log/assets/64852591/65607364-3bf7-4920-abd1-edfdbc8d4df0",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            new ArrayList<>(List.of(LONDON_DAYLOG_1, LONDON_DAYLOG_2, LONDON_DAYLOG_EXTRA))
    );
}
