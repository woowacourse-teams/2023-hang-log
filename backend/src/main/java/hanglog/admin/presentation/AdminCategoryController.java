package hanglog.admin.presentation;


import hanglog.auth.AdminAuth;
import hanglog.auth.AdminOnly;
import hanglog.auth.domain.Accessor;
import hanglog.category.dto.request.CategoryRequest;
import hanglog.category.dto.response.CategoryDetailResponse;
import hanglog.category.service.CategoryService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    @AdminOnly
    public ResponseEntity<Void> createCategory(
            @AdminAuth final Accessor accessor,
            @RequestBody @Valid final CategoryRequest categoryRequest
    ) {
        final Long categoryId = categoryService.save(categoryRequest);
        return ResponseEntity.created(URI.create("/admin/categories/" + categoryId)).build();
    }
}
