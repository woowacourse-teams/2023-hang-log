package hanglog.integration.service;

import static hanglog.integration.IntegrationFixture.ETC_CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.category.dto.request.CategoryRequest;
import hanglog.category.service.CategoryService;
import hanglog.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/categories.sql"
})
@Import(CategoryService.class)
public class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    private static final CategoryRequest CATEGORY_REQUEST = new CategoryRequest(999L, "eng", "한글");

    @DisplayName("카테고리 목록을 조회한다.")
    @Test
    void getAllCategoriesDetail() {
        // when & then
        assertThat(categoryService.getAllCategoriesDetail()).isNotEmpty();
    }

    @DisplayName("새로운 카테고리를 저장한다.")
    @Test
    void save() {
        // when & then
        assertDoesNotThrow(() -> categoryService.save(CATEGORY_REQUEST));
    }

    @DisplayName("카테고리 이름을 수정한다.")
    @Test
    void update_CategoryName() {
        // given
        final Long id = categoryService.save(CATEGORY_REQUEST);
        final CategoryRequest updateRequest = new CategoryRequest(id, "changed", "변경된카테고리");

        // when & then
        assertDoesNotThrow(() -> categoryService.update(id, updateRequest));
    }

    @DisplayName("이미 존재하는 카테고리 이름으로 수정할 수 없다.")
    @Test
    void update_CategoryNameFail() {
        // given
        final Long id = categoryService.save(CATEGORY_REQUEST);
        final CategoryRequest updateRequest = new CategoryRequest(
                id,
                ETC_CATEGORY.getEngName(),
                ETC_CATEGORY.getKorName()
        );

        // when & then
        assertThatThrownBy(() -> categoryService.update(id, updateRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("카테고리 ID를 수정한다.")
    @Test
    void update_CategoryId() {
        // given
        final Long id = categoryService.save(CATEGORY_REQUEST);
        final CategoryRequest updateRequest = new CategoryRequest(
                900L,
                CATEGORY_REQUEST.getEngName(),
                CATEGORY_REQUEST.getKorName()
        );

        // when & then
        assertDoesNotThrow(() -> categoryService.update(id, updateRequest));
    }

    @DisplayName("이미 존재하는 카테고리 ID로 수정할 수 없다.")
    @Test
    void update_CategoryIdFail() {
        // given
        final Long id = categoryService.save(CATEGORY_REQUEST);
        final CategoryRequest updateRequest = new CategoryRequest(
                ETC_CATEGORY.getId(),
                CATEGORY_REQUEST.getEngName(),
                CATEGORY_REQUEST.getKorName()
        );

        // when & then
        assertThatThrownBy(() -> categoryService.update(id, updateRequest))
                .isInstanceOf(BadRequestException.class);
    }
}
