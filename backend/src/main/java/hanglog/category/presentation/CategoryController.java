package hanglog.category.presentation;

import hanglog.category.dto.CategoryResponse;
import hanglog.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getExpenseCategories() {
        final List<CategoryResponse> categoryResponses = categoryService.getExpenseCategories();
        return ResponseEntity.ok(categoryResponses);
    }
}
