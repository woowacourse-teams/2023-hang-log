package hanglog.trip.fixture;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.type.ItemType;

public class ItemFixture {

    // TODO: ItemFixture를 위한 임시 dayLog. 이후 dayLogFixture로 분리 필요
    private static DayLog dayLog = new DayLog(
            "첫날",
            1,
            TripFixture.LONDON_TRIP);
    public static Item LONDON_EYE_ITEM = new Item(
            1L,
            ItemType.SPOT,
            "런던 아이",
            1,
            4.5,
            "런던 아이 메모",
            PlaceFixture.LONDON_EYE,
            dayLog,
            ExpenseFixture.expense
    );
    public static Item TAXI_ITEM = new Item(
            2L,
            ItemType.NON_SPOT,
            "택시",
            2,
            4.0,
            "런던에서 탄 택시",
            dayLog,
            ExpenseFixture.expense
    );
}
