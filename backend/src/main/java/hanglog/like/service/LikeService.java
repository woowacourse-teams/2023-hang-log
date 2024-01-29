package hanglog.like.service;

import static hanglog.like.domain.LikeRedisKeyConstants.generateLikeKey;
import static java.lang.Boolean.TRUE;

import hanglog.like.dto.request.LikeRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void update(final Long memberId, final Long tripId, final LikeRequest likeRequest) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        final String key = generateLikeKey(tripId);
        if (TRUE.equals(opsForSet.isMember(key, memberId))) {
            removeMemberInLike(key, memberId, likeRequest.getIsLike(), opsForSet);
            return;
        }
        addMemberInLike(key, memberId, likeRequest.getIsLike(), opsForSet);
    }

    private void removeMemberInLike(final String key, final Long memberId, final Boolean isLike,
                                    final SetOperations<String, Object> opsForSet) {
        if (!isLike) {
            opsForSet.remove(key, memberId);
            redisTemplate.expire(key, Duration.ofMinutes(90L));
        }
    }

    private void addMemberInLike(final String key, final Long memberId, final Boolean isLike,
                                 final SetOperations<String, Object> opsForSet) {
        if (isLike) {
            opsForSet.add(key, memberId);
            redisTemplate.expire(key, Duration.ofMinutes(90L));
        }
    }
}
