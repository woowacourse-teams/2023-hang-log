package hanglog.auth.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@NoArgsConstructor
@Getter
//@RedisHash(value = "refreshToken", timeToLive = 604800)
public class RefreshToken {

    @Id
    private String token;

    private Long memberId;
}
