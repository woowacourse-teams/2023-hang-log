package hanglog.like.domain;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "memberLike", timeToLive = 5400)
public class MemberLike {

    @Id
    private Long memberId;

    private Map<Long, Boolean> tripLikeStatusMap;
}
