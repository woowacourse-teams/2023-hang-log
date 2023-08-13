package hanglog.share.fixture;

import hanglog.trip.domain.City;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShareFixture {

    public static final Trip TRIP = new Trip(
            1L,
            "test",
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

    public static final City CALIFORNIA = new City(
            1L,
            "미국",
            "캘리포니아",
            BigDecimal.valueOf(37.9838096),
            BigDecimal.valueOf(23.7275388)
    );

    public static final City TOKYO = new City(
            2L,
            "일본",
            "도쿄",
            BigDecimal.valueOf(-33.9248685),
            BigDecimal.valueOf(18.4240553)
    );

    public static final City BEIJING = new City(
            3L,
            "중국",
            "베이징",
            BigDecimal.valueOf(-33.9248685),
            BigDecimal.valueOf(18.4240553)
    );

    public static void setDayLogs() {
        final List<DayLog> dayLogs = TRIP.getDayLogs();
        dayLogs.add(LONDON_DAYLOG_1);
        dayLogs.add(LONDON_DAYLOG_2);
    }
}
