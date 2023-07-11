package hanglog.trip.presentation.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class TripRequest {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotEmpty
    private List<Long> cityIds;

    public TripRequest(final LocalDate startDate, final LocalDate endDate, final List<Long> cityIds) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cityIds = cityIds;
    }
}
