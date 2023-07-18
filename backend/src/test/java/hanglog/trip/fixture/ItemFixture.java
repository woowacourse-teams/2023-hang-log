package hanglog.trip.fixture;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.type.ItemType;

public final class ItemFixture {

    private static final DayLog DAYLOG_FOR_ITEM_FIXTURE = new DayLog(
            "첫날",
            1,
            TripFixture.LONDON_TRIP);

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
}
