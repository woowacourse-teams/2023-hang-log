package hanglog.trip.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DayLogUpdateTitleRequest {

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    public DayLogUpdateTitleRequest(final String title) {
        this.title = title;
    }
}
