package hanglog.like.dto;

import hanglog.like.domain.LikeInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LikeElements {

    private final List<LikeElement> likeElements;

    public static Map<Long, LikeInfo> toLikeMap(final List<LikeElement> likeElements) {
        final Map<Long, LikeInfo> map = new HashMap<>();
        for (final LikeElement likeElement : likeElements) {
            final LikeInfo likeInfo = new LikeInfo(likeElement.getLikeCount(), likeElement.isLike());
            map.put(likeElement.getTripId(), likeInfo);
        }
        return map;
    }
}
