package hanglog.trip.fixture;

import hanglog.trip.domain.Trip;
import java.time.LocalDate;

public final class TripFixture {

    public static final Trip LONDON_TRIP = new Trip(1L,
            "런던 여행",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 7),
            "");
}
