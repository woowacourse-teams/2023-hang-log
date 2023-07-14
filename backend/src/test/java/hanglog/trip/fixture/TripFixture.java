package hanglog.trip.fixture;

import hanglog.trip.domain.Trip;
import java.time.LocalDate;

public final class TripFixture {

    public static final Trip LONDON_TRIP = new Trip(
            1L,
            "런던 여행",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 7),
            ""
    );

    public static final Trip UPDATED_LONDON_TRIP = new Trip(
            1L,
            "변경된 런던 여행 제목",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 7),
            "추가된 여행 요약"
    );
}
