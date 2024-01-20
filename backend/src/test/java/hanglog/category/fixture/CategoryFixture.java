package hanglog.category.fixture;

import hanglog.category.domain.Category;
import java.util.List;

public class CategoryFixture {

    public static final List<Category> EXPENSE_CATEGORIES = List.of(
            new Category(100L, "food", "음식"),
            new Category(200L, "culture", "문화"),
            new Category(300L, "shopping", "쇼핑"),
            new Category(400L, "accommodation", "숙박"),
            new Category(500L, "transportation", "교통"),
            new Category(600L, "etc", "기타")
    );

    public static final List<Category> CATEGORIES = List.of(
            new Category(100L, "food", "음식"),
            new Category(101L, "cafe", "카페"),
            new Category(102L, "restaurants", "식당")
    );

    public static final Category FOOD = EXPENSE_CATEGORIES.get(0);
    public static final Category CULTURE = EXPENSE_CATEGORIES.get(1);
    public static final Category SHOPPING = EXPENSE_CATEGORIES.get(2);
    public static final Category ACCOMMODATION = EXPENSE_CATEGORIES.get(3);
    public static final Category TRANSPORTATION = EXPENSE_CATEGORIES.get(4);
    public static final Category ETC = EXPENSE_CATEGORIES.get(5);
}
