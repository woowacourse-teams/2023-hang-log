package hanglog.like.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeInfo {

    private final long likeCount;
    private final boolean isLike;
}
