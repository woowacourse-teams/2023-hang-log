package hanglog.trip.fixture;

import hanglog.trip.domain.Trip;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static hanglog.trip.fixture.DayLogFixture.*;

public final class TripFixture {

    public static final Trip LONDON_TRIP = new Trip(
            1L,
            "런던 여행",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            new ArrayList<>(List.of(LONDON_DAYLOG_1, LONDON_DAYLOG_2, LONDON_DAYLOG_EXTRA))
    );
}
