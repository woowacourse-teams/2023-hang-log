package hanglog.share.dto.request;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class TripSharedStatusRequest {

    @NotNull(message = "공유 상태를 선택해주세요.")
    private Boolean sharedStatus;
}
