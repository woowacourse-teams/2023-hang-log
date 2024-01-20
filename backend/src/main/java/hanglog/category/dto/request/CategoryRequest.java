package hanglog.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "영어이름을 입력해주세요.")
    @Size(max = 50, message = "영어이름은 50자를 초과할 수 없습니다.")
    private final String engName;

    @NotBlank(message = "한글 이을 입력해주세요.")
    @Size(max = 50, message = "한글 이름은 50자를 초과할 수 없습니다.")
    private final String korName;
}
