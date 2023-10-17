package hanglog.share.fixture;

import static hanglog.integration.IntegrationFixture.MEMBER;

import hanglog.city.domain.City;
import hanglog.trip.domain.SharedTrip;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.type.PublishedStatusType;
import hanglog.trip.domain.type.SharedStatusType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShareFixture {


    public static final Trip TRIP_SHARE = new Trip(
            1L,
            MEMBER,
            "test",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            new SharedTrip(null, null, "sharedCode"),
            new ArrayList<>(),
            SharedStatusType.SHARED,
            PublishedStatusType.UNPUBLISHED
    );

    public static final Trip TRIP_UNSHARE = new Trip(
            1L,
            MEMBER,
            "test",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            new SharedTrip(null, null, "sharedCode"),
            new ArrayList<>(),
            SharedStatusType.UNSHARED,
            PublishedStatusType.UNPUBLISHED
    );

    public static final DayLog LONDON_DAYLOG_1 = new DayLog(
            1L,
            "런던 여행 1일차",
            1,
            TRIP_SHARE,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_2 = new DayLog(
            2L,
            "런던 여행 2일차",
            2,
            TRIP_SHARE,
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
    public static final SharedTrip SHARED_TRIP = new SharedTrip(1L, TRIP_SHARE, "sharedCode");
    public static final SharedTrip UNSHARED_TRIP = new SharedTrip(2L, TRIP_UNSHARE, "sharedCode");

    public static final Trip TRIP_HAS_SHARED_TRIP = new Trip(
            1L,
            MEMBER,
            "test",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            SHARED_TRIP,
            new ArrayList<>(),
            SharedStatusType.SHARED,
            PublishedStatusType.UNPUBLISHED
    );

    static {
        addDayLogsToTrip(TRIP_SHARE, Arrays.asList(LONDON_DAYLOG_1, LONDON_DAYLOG_2));
    }

    private static void addDayLogsToTrip(final Trip trip, final List<DayLog> dayLogs) {
        dayLogs.stream()
                .filter(dayLog -> !trip.getDayLogs().contains(dayLog))
                .forEachOrdered(trip::addDayLog);
    }
}
