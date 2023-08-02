package hanglog.category.fixture;

import hanglog.category.domain.Category;
import java.util.List;

public class CategoryFixture {

    public static final List<String> CATEGORY_ENG_NAMES = List.of(
            "food",
            "culture",
            "shopping",
            "accommodation",
            "transportation",
            "etc"
    );

    public static final List<Category> EXPENSE_CATEGORIES = List.of(
            new Category(100L, "food", "음식"),
            new Category(200L, "culture", "문화"),
            new Category(300L, "shopping", "쇼핑"),
            new Category(400L, "accommodation", "숙박"),
            new Category(500L, "transportation", "교통"),
            new Category(600L, "etc", "기타")
    );
}
