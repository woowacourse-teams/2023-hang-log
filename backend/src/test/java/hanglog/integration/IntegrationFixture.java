package hanglog.integration;

import static hanglog.trip.domain.type.ItemType.SPOT;

import hanglog.category.domain.Category;
import hanglog.city.domain.City;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;
import hanglog.member.domain.Member;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Image;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import hanglog.trip.domain.Trip;
import hanglog.trip.dto.request.TripCreateRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class IntegrationFixture {

    /* Member */
    public static final Member MEMBER = new Member(
            1L,
            "socialLoginId",
            "name",
            "https://hanglog.com/img/imageName.png"
    );

    /* Category */
    public static final Category FOOD_CATEGORY = new Category(100L, "food", "음식");
    public static final Category CULTURE_CATEGORY = new Category(200L, "culture", "문화");
    public static final Category UNIVERSITY_CATEGORY = new Category(264L, "university", "대학교");
    public static final Category SHOPPING_CATEGORY = new Category(300L, "shopping", "쇼핑");
    public static final Category ACCOMMODATION_CATEGORY = new Category(400L, "accommodation", "숙박");
    public static final Category TRANSPORTATION_CATEGORY = new Category(500L, "transportation", "교통");
    public static final Category ETC_CATEGORY = new Category(600L, "etc", "기타");

    /* City */
    public static final City LONDON = new City(
            1L,
            "런던",
            "영국",
            new BigDecimal("51.5072178"),
            new BigDecimal("-0.1275862")
    );

    public static final City EDINBURGH = new City(
            2L,
            "에든버러",
            "영국",
            new BigDecimal("55.953252"),
            new BigDecimal("-3.188267")
    );

    public static final City PARIS = new City(
            3L,
            "파리",
            "프랑스",
            new BigDecimal("48.856614"),
            new BigDecimal("2.3522219")
    );
    public static final City TOKYO = new City(
            4L,
            "일본",
            "도쿄",
            new BigDecimal("35.6761919"),
            new BigDecimal("139.6503106")
    );

    /* Place */
    private static final Place PICCADILLY_CIRCUS = new Place(
            "피카딜리 서커스",
            new BigDecimal("51.510173624674515"),
            new BigDecimal("-0.1349577015912248"),
            CULTURE_CATEGORY
    );

    private static final Place COVENT_GARDEN = new Place(
            "코벤트 가든",
            new BigDecimal("51.51218732746311"),
            new BigDecimal("-0.12276122266403186"),
            CULTURE_CATEGORY
    );

    private static final Place BRITISH_MUSEUM = new Place(
            "영국 박물관",
            new BigDecimal("51.519583537867035"),
            new BigDecimal("-0.12698878659703797"),
            CULTURE_CATEGORY
    );

    /* image */
    public static final Image DEFAULT_IMAGE = new Image("default-image.png");

    /* Trip */
    public static final LocalDate START_DATE = LocalDate.of(2023, 8, 1);
    public static final LocalDate END_DATE = LocalDate.of(2023, 8, 3);
    public static final Trip LAHGON_TRIP = Trip.of(MEMBER, "런던 여행", START_DATE, END_DATE);
    public static final TripCreateRequest TRIP_CREATE_REQUEST = new TripCreateRequest(
            START_DATE,
            END_DATE,
            Arrays.asList(LONDON.getId(), EDINBURGH.getId())
    );

    /* DayLog */
    private static final DayLog DAY_LOG_1 = new DayLog(
            "런던 방문기",
            1,
            LAHGON_TRIP
    );

    /* Item */
    private static final Item PICCADILLY_CIRCUS_ITEM = new Item(
            SPOT,
            "피카딜리 서커스",
            1,
            4.0,
            "북적북적한 피키달리 서커스",
            PICCADILLY_CIRCUS,
            DAY_LOG_1,
            new Expense("gbp", new Amount(200.0), CULTURE_CATEGORY),
            List.of(DEFAULT_IMAGE)
    );

    private static final Item COVENT_GARDEN_ITEM = new Item(
            SPOT,
            "코벤트 가든",
            2,
            4.5,
            "이 세상에서 제일 맛있는 애프터눈 티랑 스콘",
            COVENT_GARDEN,
            DAY_LOG_1,
            new Expense("gbp", new Amount(80.0), FOOD_CATEGORY),
            List.of(DEFAULT_IMAGE)
    );

    private static final Item BRITISH_MUSEUM_ITEM = new Item(
            SPOT,
            "대영박물관",
            3,
            3.0,
            "세계의 역사를 볼 수 있는 대영박물관",
            BRITISH_MUSEUM,
            DAY_LOG_1,
            new Expense("gbp", new Amount(0.0), CULTURE_CATEGORY),
            List.of(DEFAULT_IMAGE)
    );

    static {
        addDayLogsToTrip(LAHGON_TRIP, List.of(DAY_LOG_1));
    }

    private static void addDayLogsToTrip(final Trip trip, final List<DayLog> dayLogs) {
        dayLogs.stream()
                .filter(dayLog -> !trip.getDayLogs().contains(dayLog))
                .forEachOrdered(trip::addDayLog);
    }
}
