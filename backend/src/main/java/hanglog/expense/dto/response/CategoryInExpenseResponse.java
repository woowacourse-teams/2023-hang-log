package hanglog.expense.dto.response;


import hanglog.category.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryInExpenseResponse {

    private final long id;
    private final String name;

    public static CategoryInExpenseResponse of(final Category category) {
        return new CategoryInExpenseResponse(
                category.getId(),
                category.getKorName()
        );
    }
}
