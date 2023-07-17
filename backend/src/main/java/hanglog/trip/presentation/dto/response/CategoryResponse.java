package hanglog.trip.presentation.dto.response;

import hanglog.category.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {
    private final Long id;
    private final String korName;
    private final String engName;

    public static CategoryResponse of(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getKorName(),
                category.getEngName()
        );
    }
}
