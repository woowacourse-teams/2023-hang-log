package hanglog.trip.fixture;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;

import hanglog.trip.domain.Place;
import java.math.BigDecimal;

public final class PlaceFixture {

    public static final Place LONDON_EYE = new Place("런던아이",
            new BigDecimal("30.123456"),
            new BigDecimal("31.123456"),
            EXPENSE_CATEGORIES.get(1)
    );
}
