package hanglog.auth.domain.repository;

import hanglog.auth.domain.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

    private final RedisTemplate redisTemplate;
    private final ValueOperations<String, Long> valueOperations;

    public RefreshTokenRepository(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void save(final RefreshToken refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getToken(), refreshToken.getMemberId());
        redisTemplate.expire(refreshToken.getToken(), 60L, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findById(final String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long memberId = valueOperations.get(refreshToken);

        if (Objects.isNull(memberId)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, memberId));
    }

    public void deleteByMemberId(Long memberId) {
        // Redis에서 모든 키 가져오기
        Set<String> keys = redisTemplate.keys("*");

        // memberId와 관련된 키 삭제
        for (String key : keys) {
            Long storedMemberId = valueOperations.get(key);
            if (storedMemberId != null && storedMemberId.equals(memberId)) {
                redisTemplate.delete(key);
            }
        }
    }

}
