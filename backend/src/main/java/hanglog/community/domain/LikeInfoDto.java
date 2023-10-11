package hanglog.community.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LikeInfoDto {

    private final Long tripId;
    private final long likeCount;
    private final boolean isLike;

    public static Map<Long, LikeInfo> toLikeMap(final List<LikeInfoDto> likeInfoDtos) {
        final Map<Long, LikeInfo> map = new HashMap<>();
        for (final LikeInfoDto likeInfoDto : likeInfoDtos) {
            final LikeInfo likeInfo = new LikeInfo(likeInfoDto.getLikeCount(), likeInfoDto.isLike());
            map.put(likeInfoDto.getTripId(), likeInfo);
        }
        return map;
    }
}
