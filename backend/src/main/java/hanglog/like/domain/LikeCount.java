package hanglog.like.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "likeCount")
public class LikeCount {

    @Id
    private Long tripId;

    private Long count;
}
