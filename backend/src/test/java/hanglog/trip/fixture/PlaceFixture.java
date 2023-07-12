package hanglog.trip.fixture;

import hanglog.category.Category;
import hanglog.trip.domain.Place;
import java.math.BigDecimal;

public class PlaceFixture {

    public static final Place LONDON_EYE = new Place("런던아이",
            "런던아이주소",
            new BigDecimal(30.123456),
            new BigDecimal(31.123456),
            new Category("문화", "apiId"));
}
