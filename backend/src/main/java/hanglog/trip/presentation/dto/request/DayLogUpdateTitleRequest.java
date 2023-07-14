package hanglog.trip.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DayLogUpdateTitleRequest {

    @NotNull(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "날짜별 제목은 50자를 초과할 수 없습니다.")
    private String title;

    public DayLogUpdateTitleRequest(final String title) {
        this.title = title;
    }
}
