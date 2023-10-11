package hanglog.community.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeInfoByTrip {

    private final Long tripId;
    private final long likeCount;
    private final boolean isLike;

    public static Map<Long, LikeInfo> toLikeMap(final List<LikeInfoByTrip> likeInfoByTrips) {
        final Map<Long, LikeInfo> map = new HashMap<>();
        for (final LikeInfoByTrip likeInfoByTrip : likeInfoByTrips) {
            final LikeInfo likeInfo = new LikeInfo(likeInfoByTrip.getLikeCount(), likeInfoByTrip.isLike());
            map.put(likeInfoByTrip.getTripId(), likeInfo);
        }
        return map;
    }
}
