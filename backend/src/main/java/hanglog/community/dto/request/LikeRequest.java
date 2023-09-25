package hanglog.community.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequest {

    @NotNull(message = "좋아요 상태 변경값은 필수입니다.")
    private final Boolean isLike;
}
