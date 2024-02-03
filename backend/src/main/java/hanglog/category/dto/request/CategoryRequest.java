package hanglog.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRequest {

    @NotNull(message = "카테고리 ID를 입력해주세요.")
    private final Long id;

    @NotBlank(message = "영어이름을 입력해주세요.")
    @Size(max = 50, message = "영어이름은 50자를 초과할 수 없습니다.")
    private final String engName;

    @NotBlank(message = "한글 이름을 입력해주세요.")
    @Size(max = 50, message = "한글 이름은 50자를 초과할 수 없습니다.")
    private final String korName;
}
