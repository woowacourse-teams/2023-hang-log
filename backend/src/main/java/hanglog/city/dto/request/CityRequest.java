package hanglog.city.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CityRequest {

    @NotBlank(message = "도시의 이름을 입력해주세요.")
    @Size(max = 20, message = "도시의 이름은 20자를 초과할 수 없습니다.")
    private final String name;

    @NotBlank(message = "나라 이름을 입력해주세요.")
    @Size(max = 20, message = "나라 이름은 20자를 초과할 수 없습니다.")
    private final String country;

    @NotNull(message = "도시의 위도를 입력해주세요.")
    @Digits(integer = 3, fraction = 13)
    private final BigDecimal latitude;

    @NotNull(message = "도시의 경도를 입력해주세요.")
    @Digits(integer = 3, fraction = 13)
    private final BigDecimal longitude;
}
