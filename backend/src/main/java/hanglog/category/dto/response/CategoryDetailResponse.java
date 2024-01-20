package hanglog.category.dto.response;

import hanglog.category.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryDetailResponse {

    private final Long id;
    private final String engName;
    private final String korName;

    public static CategoryDetailResponse of(final Category category) {
        return new CategoryDetailResponse(
                category.getId(),
                category.getEngName(),
                category.getKorName()
        );
    }
}
