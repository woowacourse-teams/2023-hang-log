package hanglog.category.dto;

import hanglog.category.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {

    private final Long id;
    private final String name;

    public static CategoryResponse of(final Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getKorName()
        );
    }
}
