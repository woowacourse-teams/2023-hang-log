package hanglog.community.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LikeInfo {

    private final long likeCount;
    private final boolean isLike;


}
