package hanglog.community.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {

    @NotNull(message = "좋아요 상태 변경값은 필수입니다.")
    private Boolean isLike;
}
