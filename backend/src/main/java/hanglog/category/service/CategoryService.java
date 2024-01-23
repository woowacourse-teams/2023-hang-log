package hanglog.category.service;

import static hanglog.global.exception.ExceptionCode.DUPLICATED_CATEGORY_ID;
import static hanglog.global.exception.ExceptionCode.DUPLICATED_CATEGORY_NAME;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CATEGORY_ID;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.category.dto.request.CategoryRequest;
import hanglog.category.dto.response.CategoryDetailResponse;
import hanglog.category.dto.response.CategoryResponse;
import hanglog.global.exception.BadRequestException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getExpenseCategories() {
        final List<Category> expenseCategories = categoryRepository.findExpenseCategory();
        return expenseCategories.stream()
                .map(CategoryResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoryDetailResponse> getAllCategoriesDetail() {
        final List<Category> expenseCategories = categoryRepository.findAll();
        return expenseCategories.stream()
                .map(CategoryDetailResponse::of)
                .toList();
    }

    public Long save(final CategoryRequest categoryRequest) {
        validateCategoryDuplicateId(categoryRequest);
        validateCategoryDuplicateName(categoryRequest);

        return categoryRepository.save(Category.of(categoryRequest)).getId();
    }

    private void validateCategoryDuplicateId(final CategoryRequest categoryRequest) {
        if (categoryRepository.existsById(categoryRequest.getId())) {
            throw new BadRequestException(DUPLICATED_CATEGORY_ID);
        }
    }

    private void validateCategoryDuplicateName(final CategoryRequest categoryRequest) {
        if (categoryRepository.existsByEngNameAndKorName(categoryRequest.getEngName(), categoryRequest.getKorName())) {
            throw new BadRequestException(DUPLICATED_CATEGORY_NAME);
        }
    }


    public void update(final Long id, final CategoryRequest categoryRequest) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CATEGORY_ID));

        validateCategoryDuplicate(category, categoryRequest);

        category.update(categoryRequest);
    }

    private void validateCategoryDuplicate(final Category category, final CategoryRequest categoryRequest) {
        if (!category.getId().equals(categoryRequest.getId())) {
            validateCategoryDuplicateId(categoryRequest);
        }
        if (!category.isSameNames(categoryRequest.getEngName(), categoryRequest.getKorName())) {
            validateCategoryDuplicateName(categoryRequest);
        }
    }
}
