package hanglog.trip.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class PlaceRequest {

    @NotNull(message = "장소의 API ID를 입력해주세요.")
    private final String apiId;

    @NotNull(message = "장소의 이름을 입력해주세요.")
    @Size(max = 50, message = "장소의 이름은 50자를 초과할 수 없습니다.")
    private final String name;

    @NotNull(message = "장소의 주소를 입력해주세요.")
    @Size(max = 255, message = "장소의 주소는 255자를 초과할 수 없습니다.")
    private final String address;

    @NotNull(message = "장소의 위도를 입력해주세요.")
    private final BigDecimal latitude;

    @NotNull(message = "장소의 경도를 입력해주세요.")
    private final BigDecimal longitude;

    @NotNull(message = "장소의 카데고리 API ID를 입력해주세요.")
    private final String categoryApiId;
}
