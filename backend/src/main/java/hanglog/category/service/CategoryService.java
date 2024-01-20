package hanglog.category.service;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.category.dto.response.CategoryDetailResponse;
import hanglog.category.dto.response.CategoryResponse;
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
}
