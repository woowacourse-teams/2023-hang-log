package hanglog.trip.fixture;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.type.ItemType;
import java.util.List;

public final class ItemFixture {

    public static final DayLog DAYLOG_FOR_ITEM_FIXTURE = new DayLog(
            "첫날",
            1,
            TripFixture.LONDON_TRIP
    );

    public static final Item LONDON_EYE_ITEM = new Item(
            1L,
            ItemType.SPOT,
            "런던 아이",
            1,
            4.5,
            "런던 아이 메모",
            PlaceFixture.LONDON_EYE,
            DAYLOG_FOR_ITEM_FIXTURE,
            ExpenseFixture.EURO_10000
    );

    public static final Item TAXI_ITEM = new Item(
            2L,
            ItemType.NON_SPOT,
            "택시",
            2,
            4.0,
            "런던에서 탄 택시",
            DAYLOG_FOR_ITEM_FIXTURE,
            ExpenseFixture.EURO_10000
    );

    public static final Item AIRPLANE_ITEM = new Item(
            3L,
            ItemType.NON_SPOT,
            "비행기",
            3,
            4.5,
            "런던에서 탄 비행기",
            DAYLOG_FOR_ITEM_FIXTURE,
            ExpenseFixture.EURO_10000
    );

    public static final Item JAPAN_HOTEL = new Item(
            4L,
            ItemType.NON_SPOT,
            "호텔",
            4,
            4.5,
            "일본에서 묵은 호텔",
            new DayLog(
                    "이동",
                    1,
                    TripFixture.LONDON_TRIP
            ),
            ExpenseFixture.JPY_10000
    );

    static {
        addItemsToDayLog(DAYLOG_FOR_ITEM_FIXTURE, List.of(LONDON_EYE_ITEM, AIRPLANE_ITEM, TAXI_ITEM, JAPAN_HOTEL));
    }

    private static void addItemsToDayLog(final DayLog dayLog, final List<Item> items) {
        items.stream()
                .filter(item -> !dayLog.getItems().contains(item))
                .forEachOrdered(dayLog::addItem);
    }
}
