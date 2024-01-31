package hanglog.like.dto;

import java.util.Collections;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeElement {

    private final Long tripId;
    private final long likeCount;
    private final Set<Long> memberIds;

    public boolean isLike(final Long memberId) {
        return memberIds.contains(memberId);
    }

    public static LikeElement empty(final Long tripId) {
        return new LikeElement(tripId, 0, Collections.emptySet());
    }
}
