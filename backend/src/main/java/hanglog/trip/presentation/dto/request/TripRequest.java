package hanglog.trip.presentation.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class TripRequest {

    @NotNull(message = "여행 시작 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "여행 종료 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "여행한 도시는 최소 한 개 이상 입력해주세요.")
    @NotEmpty(message = "여행한 도시는 최소 한 개 이상 입력해주세요.")
    private List<Long> cityIds;

    public TripRequest(final LocalDate startDate, final LocalDate endDate, final List<Long> cityIds) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cityIds = cityIds;
    }
}
