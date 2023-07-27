package hanglog.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.category.dto.CategoryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @DisplayName("경비 구분에 필요한 상위 카테고리 정보를 반환한다.")
    @Test
    void getExpenseCategories() {
        // given
        final List<Category> expenseCategory = List.of(
                new Category(100L, "food", "음식"),
                new Category(200L, "culture", "문화"),
                new Category(300L, "shopping", "쇼핑"),
                new Category(400L, "accommodation", "숙박"),
                new Category(500L, "transportation", "교통"),
                new Category(600L, "etc", "기타")
        );

        final List<CategoryResponse> expectResponse = expenseCategory.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getKorName(), category.getEngName()))
                .toList();

        given(categoryRepository.findExpenseCategory())
                .willReturn(expenseCategory);

        // when
        final List<CategoryResponse> actualResponses = categoryService.getExpenseCategories();

        // then
        assertThat(actualResponses).usingRecursiveComparison().isEqualTo(expectResponse);
    }
}
