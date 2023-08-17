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
public class TripCreateRequest {

    @NotNull(message = "여행 시작 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startDate;

    @NotNull(message = "여행 종료 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endDate;

    @NotEmpty(message = "여행한 도시는 최소 한 개 이상 입력해 주세요.")
    private final List<Long> cityIds;
}
