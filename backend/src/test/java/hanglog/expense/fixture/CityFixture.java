package hanglog.expense.fixture;

import hanglog.trip.domain.City;
import java.math.BigDecimal;

public class CityFixture {

    public static final City PARIS = new City(
            1L,
            "파리",
            "프랑스",
            new BigDecimal("123.456"),
            new BigDecimal("654.321")
    );

    public static final City LONDON = new City(
            2L,
            "런던",
            "영국",
            new BigDecimal("789.000"),
            new BigDecimal("987.098")
    );
    public static final City TOKYO = new City(
            3L,
            "일본",
            "도쿄",
            new BigDecimal("404.000"),
            new BigDecimal("303.099")
    );
}
