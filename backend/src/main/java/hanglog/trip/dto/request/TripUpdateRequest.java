package hanglog.trip.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class TripUpdateRequest {

    @NotNull(message = "여행 제목을 입력해 주세요.")
    @Size(max = 50, message = "여행 제목은 50자를 넘을 수 없습니다.")
    private final String title;

    @NotNull(message = "여행 시작 날짜를 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startDate;

    @NotNull(message = "여행 종료 날짜를 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endDate;

    @Size(max = 200, message = "여행 요약은 200자를 넘을 수 없습니다.")
    private final String description;

    @NotNull(message = "여행한 도시는 최소 한 개 이상 입력해 주세요.")
    @NotEmpty(message = "여행한 도시는 최소 한 개 이상 입력해 주세요.")
    private final List<Long> cityIds;
}
