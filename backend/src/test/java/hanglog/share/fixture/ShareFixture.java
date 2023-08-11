package hanglog.share.fixture;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;

import hanglog.category.dto.CategoryResponse;
import hanglog.expense.dto.response.ItemExpenseResponse;
import hanglog.trip.domain.City;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.dto.response.CityWithPositionResponse;
import hanglog.trip.dto.response.DayLogGetResponse;
import hanglog.trip.dto.response.ItemResponse;
import hanglog.trip.dto.response.PlaceResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShareFixture {

    public static final Trip LONDON_TRIP = new Trip(
            1L,
            "런던 여행",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            null,
            new ArrayList<>()
    );

    public static final DayLog LONDON_DAYLOG_1 = new DayLog(
            1L,
            "런던 여행 1일차",
            1,
            LONDON_TRIP,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_2 = new DayLog(
            2L,
            "런던 여행 2일차",
            2,
            LONDON_TRIP,
            List.of()
    );

    public static final DayLog LONDON_DAYLOG_EXTRA = new DayLog(
            3L,
            "경비 페이지",
            3,
            LONDON_TRIP,
            List.of()
    );

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
    public static final DayLogGetResponse DAY_LOG_5 = new DayLogGetResponse(
            5L,
            "0805",
            5,
            LocalDate.of(2023, 8, 5),
            List.of()
    );
    public static final DayLogGetResponse DAY_LOG_6 = new DayLogGetResponse(
            6L,
            "0806",
            6,
            LocalDate.of(2023, 8, 6),
            List.of()
    );
    public static final DayLogGetResponse DAY_LOG_7 = new DayLogGetResponse(
            7L,
            "0807",
            7,
            LocalDate.of(2023, 8, 7),
            List.of()
    );
    public static final DayLogGetResponse DAY_LOG_ETC = new DayLogGetResponse(
            8L,
            "etc",
            8,
            LocalDate.of(2023, 8, 8),
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

    private static final CategoryResponse CATEGORY_FOOD = new CategoryResponse(100L, "음식");
    private static final CategoryResponse CATEGORY_CULTURE = new CategoryResponse(200L, "문화");
    private static final CategoryResponse CATEGORY_UNIVERSITY = new CategoryResponse(264L, "대학교");
    private static final CategoryResponse CATEGORY_SHOPPING = new CategoryResponse(300L, "쇼핑");
    private static final CategoryResponse CATEGORY_ACCOMMODATION = new CategoryResponse(400L, "숙박");
    private static final CategoryResponse CATEGORY_TRANSPORTATION = new CategoryResponse(500L, "교통");
    private static final CategoryResponse CATEGORY_ETC = new CategoryResponse(600L, "기타");
    private static final PlaceResponse ITEM_1_PLACE = new PlaceResponse(
            1L,
            "캘리포니아 대학교",
            new BigDecimal("37.8701510000000"),
            new BigDecimal("-122.2594606000000"),
            CATEGORY_UNIVERSITY
    );
    private static final ItemExpenseResponse ITEM_1_EXPENSE = new ItemExpenseResponse(
            1L,
            "usd",
            10.0,
            CATEGORY_FOOD
    );
    public static final ItemResponse ITEM_1 = new ItemResponse(
            1L,
            true,
            "캘리포니아 대학 제목",
            1,
            3.5,
            "캘리포니아 학식 메모",
            List.of(convertNameToUrl("test1.jpeg")),
            ITEM_1_PLACE,
            ITEM_1_EXPENSE
    );
    private static final PlaceResponse ITEM_2_PLACE = new PlaceResponse(
            2L,
            "캘리포니아, 어바인 대학",
            new BigDecimal("33.6423814000000"),
            new BigDecimal("-117.8416747000000"),
            CATEGORY_UNIVERSITY
    );
    private static final ItemExpenseResponse ITEM_2_EXPENSE = new ItemExpenseResponse(
            2L,
            "usd",
            5.0,
            CATEGORY_CULTURE
    );
    public static final ItemResponse ITEM_2 = new ItemResponse(
            2L,
            true,
            "캘리포니아 어바인 대학 제목",
            2,
            3.5,
            "캘리포니아 어바인 메모",
            List.of(convertNameToUrl("test2.jpeg")),
            ITEM_2_PLACE,
            ITEM_2_EXPENSE
    );
    private static final ItemExpenseResponse ITEM_3_EXPENSE = new ItemExpenseResponse(
            3L,
            "usd",
            100.0,
            CATEGORY_SHOPPING
    );
    public static final ItemResponse ITEM_3 = new ItemResponse(
            3L,
            false,
            "캘리포니아 어바인 쇼핑 제목",
            3,
            4.5,
            "캘리포니아 어바인 쇼핑 메모",
            List.of(convertNameToUrl("test3.jpeg")),
            null,
            ITEM_3_EXPENSE
    );
    public static final DayLogGetResponse DAY_LOG_1 = new DayLogGetResponse(
            1L,
            "0801",
            1,
            LocalDate.of(2023, 8, 1),
            List.of(ITEM_1, ITEM_2, ITEM_3)
    );
    private static final ItemExpenseResponse ITEM_4_EXPENSE = new ItemExpenseResponse(
            4L,
            "jpy",
            1000.0,
            CATEGORY_ACCOMMODATION
    );
    public static final ItemResponse ITEM_4 = new ItemResponse(
            4L,
            false,
            "일본",
            1,
            2.5,
            "일식 식사",
            List.of(convertNameToUrl("test1.jpeg")),
            null,
            ITEM_4_EXPENSE
    );
    public static final DayLogGetResponse DAY_LOG_2 = new DayLogGetResponse(
            2L,
            "0802",
            2,
            LocalDate.of(2023, 8, 2),
            List.of(ITEM_4)
    );
    private static final ItemExpenseResponse ITEM_5_EXPENSE = new ItemExpenseResponse(
            5L,
            "cny",
            200.0,
            CATEGORY_TRANSPORTATION
    );
    public static final ItemResponse ITEM_5 = new ItemResponse(
            5L,
            false,
            "중국",
            1,
            2.5,
            "중국 택시",
            List.of(convertNameToUrl("test1.jpeg")),
            null,
            ITEM_5_EXPENSE
    );
    public static final DayLogGetResponse DAY_LOG_3 = new DayLogGetResponse(
            3L,
            "0803",
            3,
            LocalDate.of(2023, 8, 3),
            List.of(ITEM_5)
    );
    private static final ItemExpenseResponse ITEM_6_EXPENSE = new ItemExpenseResponse(
            6L,
            "eur",
            200.0,
            CATEGORY_ETC
    );
    public static final ItemResponse ITEM_6 = new ItemResponse(
            6L,
            false,
            "유럽",
            1,
            2.5,
            "유우럽",
            List.of(convertNameToUrl("test1.jpeg")),
            null,
            ITEM_6_EXPENSE
    );
    public static final DayLogGetResponse DAY_LOG_4 = new DayLogGetResponse(
            4L,
            "0804",
            4,
            LocalDate.of(2023, 8, 4),
            List.of(ITEM_6)
    );
    public static final TripDetailResponse TRIP = new TripDetailResponse(
            1L,
            List.of(CityWithPositionResponse.of(CALIFORNIA), CityWithPositionResponse.of(TOKYO)),
            "test",
            LocalDate.of(2023, 8, 1),
            LocalDate.of(2023, 8, 7),
            "description",
            "123.jpeg",
            List.of(DAY_LOG_1, DAY_LOG_2, DAY_LOG_3, DAY_LOG_4, DAY_LOG_5, DAY_LOG_6, DAY_LOG_7, DAY_LOG_ETC)
    );

    public static void setDayLogs() {
        final List<DayLog> dayLogs = LONDON_TRIP.getDayLogs();
        dayLogs.add(LONDON_DAYLOG_1);
        dayLogs.add(LONDON_DAYLOG_2);
        dayLogs.add(LONDON_DAYLOG_EXTRA);
    }
}
