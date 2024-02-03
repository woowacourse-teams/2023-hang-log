package hanglog.category.service;

import static hanglog.category.fixture.CategoryFixture.CATEGORIES;
import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.category.fixture.CategoryFixture.FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.category.dto.request.CategoryRequest;
import hanglog.category.dto.response.CategoryDetailResponse;
import hanglog.category.dto.response.CategoryResponse;
import hanglog.global.exception.BadRequestException;
import java.util.List;
import java.util.Optional;
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
        final List<CategoryResponse> expectResponses = EXPENSE_CATEGORIES.stream()
                .map(CategoryResponse::of)
                .toList();

        given(categoryRepository.findExpenseCategory())
                .willReturn(EXPENSE_CATEGORIES);

        // when
        final List<CategoryResponse> actualResponses = categoryService.getExpenseCategories();

        // then
        assertThat(actualResponses).usingRecursiveComparison().isEqualTo(expectResponses);
    }

    @DisplayName("모든 카테고리의 세부 정보를 반환한다.")
    @Test
    void getAllCategoriesDetail() {
        // given
        final List<CategoryDetailResponse> responses = CATEGORIES.stream()
                .map(CategoryDetailResponse::of)
                .toList();

        given(categoryRepository.findAll())
                .willReturn(CATEGORIES);

        // when
        final List<CategoryDetailResponse> actualResponses = categoryService.getAllCategoriesDetail();

        // then
        assertThat(actualResponses).usingRecursiveComparison().isEqualTo(responses);
    }

    @DisplayName("새로운 카테고리를 추가한다.")
    @Test
    void save() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest(1L, FOOD.getEngName(), FOOD.getKorName());

        given(categoryRepository.existsById(anyLong())).willReturn(false);
        given(categoryRepository.existsByEngName(anyString())).willReturn(false);
        given(categoryRepository.save(any(Category.class))).willReturn(FOOD);

        // when
        final Long actualId = categoryService.save(categoryRequest);

        // then
        assertThat(actualId).isEqualTo(FOOD.getId());
    }

    @DisplayName("중복된 카테고리를 추가할 수 없다.")
    @Test
    void save_DuplicateFail() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest(1L, FOOD.getEngName(), FOOD.getKorName());

        given(categoryRepository.existsById(anyLong())).willReturn(false);
        given(categoryRepository.existsByEngName(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> categoryService.save(categoryRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("카테고리 정보를 수정한다.")
    @Test
    void update() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest(1L, "newName", "새이름");

        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(FOOD));
        given(categoryRepository.existsById(anyLong())).willReturn(false);
        given(categoryRepository.existsByEngName(anyString())).willReturn(false);

        // when & then
        assertDoesNotThrow(() -> categoryService.update(FOOD.getId(), categoryRequest));
    }

    @DisplayName("수정한 카테고리의 이름이 다른 카테고리와 중복되면 예외가 발생한다.")
    @Test
    void update_DuplicateFail() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest(1L, "newName", FOOD.getKorName());

        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(FOOD));
        given(categoryRepository.existsById(anyLong())).willReturn(false);
        given(categoryRepository.existsByEngName(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> categoryService.update(FOOD.getId(), categoryRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("ID에 해당하는 카테고리가 존재하지 않으면 예외가 발생한다.")
    @Test
    void update_NotFoundFail() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest(1L, "newName", FOOD.getKorName());

        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> categoryService.update(FOOD.getId(), categoryRequest))
                .isInstanceOf(BadRequestException.class);
    }
}
