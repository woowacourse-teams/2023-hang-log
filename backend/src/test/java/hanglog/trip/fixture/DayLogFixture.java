package hanglog.trip.fixture;

import static hanglog.integration.IntegrationFixture.MEMBER;

import hanglog.community.domain.type.PublishedStatusType;
import hanglog.share.domain.type.SharedStatusType;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.type.ItemType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DayLogFixture {

    private static final Trip TRIP = new Trip(
            1L,
            MEMBER,
            "런던 여행",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            null,
            new ArrayList<>(),
            SharedStatusType.UNSHARED,
            PublishedStatusType.UNPUBLISHED
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
            new ArrayList<>()
    );
    public static final DayLog EXPENSE_JAPAN_DAYLOG = new DayLog(
            1L,
            "경비 확인 런던 여행",
            2,
            TRIP,
            new ArrayList<>()
    );

    public static final Item LONDON_EYE_ITEM = new Item(
            1L,
            ItemType.SPOT,
            "런던 아이",
            1,
            4.5,
            "런던 아이 메모",
            PlaceFixture.LONDON_EYE,
            EXPENSE_LONDON_DAYLOG,
            ExpenseFixture.EURO_10000
    );
    public static final Item AIRPLANE_ITEM = new Item(
            3L,
            ItemType.NON_SPOT,
            "비행기",
            3,
            4.5,
            "런던에서 탄 비행기",
            EXPENSE_LONDON_DAYLOG,
            ExpenseFixture.EURO_10000
    );
    public static final Item JAPAN_HOTEL = new Item(
            4L,
            ItemType.NON_SPOT,
            "호텔",
            3,
            4.5,
            "일본에서 묵은 호텔",
            EXPENSE_JAPAN_DAYLOG,
            ExpenseFixture.JPY_10000
    );

    static {
        addDayLogsToTrip(TRIP, Arrays.asList(EXPENSE_LONDON_DAYLOG, EXPENSE_JAPAN_DAYLOG));
        addItemsToDayLog(EXPENSE_LONDON_DAYLOG, List.of(LONDON_EYE_ITEM, AIRPLANE_ITEM));
        addItemsToDayLog(EXPENSE_JAPAN_DAYLOG, List.of(JAPAN_HOTEL));
    }

    private static void addDayLogsToTrip(final Trip trip, final List<DayLog> dayLogs) {
        dayLogs.stream()
                .filter(dayLog -> !trip.getDayLogs().contains(dayLog))
                .forEachOrdered(trip::addDayLog);
    }

    private static void addItemsToDayLog(final DayLog dayLog, final List<Item> items) {
        items.stream()
                .filter(item -> !dayLog.getItems().contains(item))
                .forEachOrdered(dayLog::addItem);
    }
}
