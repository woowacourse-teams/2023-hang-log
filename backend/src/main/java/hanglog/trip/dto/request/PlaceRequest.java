package hanglog.trip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceRequest {

    @NotBlank(message = "장소의 이름을 입력해주세요.")
    @Size(max = 50, message = "장소의 이름은 50자를 초과할 수 없습니다.")
    private final String name;

    @NotNull(message = "장소의 위도를 입력해주세요.")
    private final BigDecimal latitude;

    @NotNull(message = "장소의 경도를 입력해주세요.")
    private final BigDecimal longitude;

    @NotEmpty(message = "장소의 카테고리를 입력해주세요.")
    private final List<String> apiCategory;
}
