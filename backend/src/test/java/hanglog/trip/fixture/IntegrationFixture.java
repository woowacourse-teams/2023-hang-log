package hanglog.trip.fixture;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;

import hanglog.category.dto.CategoryResponse;
import hanglog.expense.dto.response.ItemExpenseResponse;
import hanglog.trip.dto.response.DayLogGetResponse;
import hanglog.trip.dto.response.ItemResponse;
import hanglog.trip.dto.response.PlaceResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class IntegrationFixture {

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
}
