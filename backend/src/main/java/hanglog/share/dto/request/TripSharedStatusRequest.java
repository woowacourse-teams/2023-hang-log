package hanglog.share.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TripSharedStatusRequest {

    @NotNull(message = "공유 상태를 선택해주세요.")
    private final Boolean sharedStatus;
}
