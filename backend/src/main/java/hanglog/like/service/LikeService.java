package hanglog.like.service;

import static hanglog.like.domain.LikeRedisConstants.EMPTY_MARKER;
import static hanglog.like.domain.LikeRedisConstants.LIKE_TTL;
import static hanglog.like.domain.LikeRedisConstants.generateLikeKey;
import static java.lang.Boolean.FALSE;

import hanglog.like.domain.repository.CustomLikeRepository;
import hanglog.like.dto.LikeElement;
import hanglog.like.dto.request.LikeRequest;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final CustomLikeRepository customLikeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void update(final Long memberId, final Long tripId, final LikeRequest likeRequest) {
        final String key = generateLikeKey(tripId);
        if (FALSE.equals(redisTemplate.hasKey(key))) {
            final LikeElement likeElement = customLikeRepository.findLikesElementByTripId(tripId)
                    .orElse(LikeElement.empty(tripId));
            storeLikeInCache(likeElement);
        }
        updateToCache(key, memberId, likeRequest.getIsLike());
    }

    private void storeLikeInCache(final LikeElement likeElement) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        final String key = generateLikeKey(likeElement.getTripId());
        opsForSet.add(key, EMPTY_MARKER);
        final Set<Long> memberIds = likeElement.getMemberIds();
        if (!memberIds.isEmpty()) {
            opsForSet.add(key, likeElement.getMemberIds().toArray());
        }
        redisTemplate.expire(key, LIKE_TTL);
    }

    private void updateToCache(final String key, final Long memberId, final Boolean isLike) {
        if (isLike) {
            addMember(key, memberId);
            return;
        }
        removeMember(key, memberId);
    }

    private void addMember(final String key, final Long memberId) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(key, memberId);
        redisTemplate.expire(key, LIKE_TTL);
    }

    private void removeMember(final String key, final Long memberId) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.remove(key, memberId);
        redisTemplate.expire(key, LIKE_TTL);
    }
}
