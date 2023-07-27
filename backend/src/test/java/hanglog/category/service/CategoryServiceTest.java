package hanglog.category.service;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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
        final List<CategoryResponse> expectResponse = EXPENSE_CATEGORIES.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getKorName(), category.getEngName()))
                .toList();

        given(categoryRepository.findExpenseCategory())
                .willReturn(EXPENSE_CATEGORIES);

        // when
        final List<CategoryResponse> actualResponses = categoryService.getExpenseCategories();

        // then
        assertThat(actualResponses).usingRecursiveComparison().isEqualTo(expectResponse);
    }
}
