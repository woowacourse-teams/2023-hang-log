package hanglog.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {
    private final Long id;
    private final String korName;
    private final String engName;

    public static CategoryResponse of(final Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getKorName(),
                category.getEngName()
        );
    }
}
