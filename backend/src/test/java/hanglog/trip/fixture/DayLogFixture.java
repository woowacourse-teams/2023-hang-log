package hanglog.trip.fixture;

import static hanglog.trip.fixture.ItemFixture.AIRPLANE_ITEM;
import static hanglog.trip.fixture.ItemFixture.JAPAN_HOTEL;
import static hanglog.trip.fixture.ItemFixture.LONDON_EYE_ITEM;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class DayLogFixture {

    private static final Trip TRIP = new Trip(
            1L,
            "런던 여행",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            null,
            new ArrayList<>()
    );

    public static final DayLog LONDON_DAYLOG_1 = new DayLog(
            1L,
            "런던 여행 1일차",
            1,
            TRIP,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_2 = new DayLog(
            2L,
            "런던 여행 2일차",
            2,
            TRIP,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_EXTRA = new DayLog(
            3L,
            "경비 페이지",
            3,
            TRIP,
            List.of()
    );

    public static final DayLog UPDATED_LONDON_DAYLOG = new DayLog(
            1L,
            "수정된 런던 여행",
            1,
            TRIP,
            List.of()
    );

    public static final DayLog EXPENSE_LONDON_DAYLOG = new DayLog(
            1L,
            "경비 확인 런던 여행",
            1,
            TRIP,
            List.of(LONDON_EYE_ITEM, AIRPLANE_ITEM)
    );
    public static final DayLog EXPENSE_JAPAN_DAYLOG = new DayLog(
            1L,
            "경비 확인 런던 여행",
            2,
            TRIP,
            List.of(JAPAN_HOTEL)
    );
}
