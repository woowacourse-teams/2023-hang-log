package hanglog.admin.presentation;


import hanglog.auth.AdminAuth;
import hanglog.auth.AdminOnly;
import hanglog.auth.domain.Accessor;
import hanglog.category.dto.response.CategoryDetailResponse;
import hanglog.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @AdminOnly
    public ResponseEntity<List<CategoryDetailResponse>> getCategoriesDetail(
            @AdminAuth final Accessor accessor
    ) {
        final List<CategoryDetailResponse> categoriesDetail = categoryService.getAllCategoriesDetail();
        return ResponseEntity.ok(categoriesDetail);
    }
}
