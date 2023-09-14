package hanglog.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor
@Getter
@RedisHash(value = "refreshToken", timeToLive = 604800)
public class RefreshToken {

    @Id
    private Long memberId;

    @Indexed
    private String token;

    public RefreshToken(final Long memberId, final String token) {
        this.memberId = memberId;
        this.token = token;
    }
}
