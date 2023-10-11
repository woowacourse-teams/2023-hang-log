package hanglog.expense.fixture;

import static hanglog.expense.fixture.ExpenseFixture.EUR_100_SHOPPING_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.KRW_100_FOOD_EXPENSE;
import static hanglog.expense.fixture.ExpenseFixture.USD_100_ACCOMMODATION_EXPENSE;
import static hanglog.integration.IntegrationFixture.MEMBER;

import hanglog.community.domain.type.PublishedStatusType;
import hanglog.trip.domain.type.SharedStatusType;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.type.ItemType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripExpenseFixture {

    public static final Trip TRIP_FOR_EXPENSE = new Trip(
            1L,
            MEMBER,
            "비용 테스트를 위한 여행",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            null,
            new ArrayList<>(),
            SharedStatusType.UNSHARED,
            PublishedStatusType.UNPUBLISHED
    );

    public static final DayLog DAYLOG_1_FOR_EXPENSE = new DayLog(
            1L,
            "비용 테스트를 위한 데이로그1",
            1,
            TRIP_FOR_EXPENSE,
            new ArrayList<>()
    );

    public static final Item KRW_100_FOOD_ITEM = new Item(
            1L,
            ItemType.NON_SPOT,
            "택시",
            1,
            4.0,
            "런던에서 탄 택시",
            DAYLOG_1_FOR_EXPENSE,
            KRW_100_FOOD_EXPENSE
    );

    public static final Item EUR_100_SHOPPING_ITEM = new Item(
            2L,
            ItemType.NON_SPOT,
            "택시",
            2,
            4.0,
            "런던에서 탄 택시",
            DAYLOG_1_FOR_EXPENSE,
            EUR_100_SHOPPING_EXPENSE
    );

    public static final DayLog DAYLOG_2_FOR_EXPENSE = new DayLog(
            2L,
            "비용 테스트를 위한 데이로그2",
            2,
            TRIP_FOR_EXPENSE,
            new ArrayList<>()
    );

    public static final Item USD_100_ACCOMMODATION_ITEM = new Item(
            4L,
            ItemType.NON_SPOT,
            "택시",
            1,
            4.0,
            "런던에서 탄 택시",
            DAYLOG_2_FOR_EXPENSE,
            USD_100_ACCOMMODATION_EXPENSE
    );

    static {
        addDayLogsToTrip(TRIP_FOR_EXPENSE, Arrays.asList(DAYLOG_1_FOR_EXPENSE, DAYLOG_2_FOR_EXPENSE));

        addItemsToDayLog(DAYLOG_1_FOR_EXPENSE, List.of(KRW_100_FOOD_ITEM, EUR_100_SHOPPING_ITEM));
        addItemsToDayLog(DAYLOG_2_FOR_EXPENSE, List.of(USD_100_ACCOMMODATION_ITEM));
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
