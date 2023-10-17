package hanglog.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeElement {

    private final Long tripId;
    private final long likeCount;
    private final boolean isLike;
}
