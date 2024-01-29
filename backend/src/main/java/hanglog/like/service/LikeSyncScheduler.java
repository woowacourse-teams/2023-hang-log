package hanglog.like.service;

import static hanglog.like.domain.LikeRedisConstants.EMPTY_MARKER;
import static hanglog.like.domain.LikeRedisConstants.LIKE_KEY_PREFIX;
import static hanglog.like.domain.LikeRedisConstants.KEY_SEPARATOR;
import static hanglog.like.domain.LikeRedisConstants.generateLikeKey;

import hanglog.like.domain.Likes;
import hanglog.like.domain.repository.CustomLikeRepository;
import hanglog.like.domain.repository.LikeRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeSyncScheduler {

    private final LikeRepository likeRepository;
    private final CustomLikeRepository customLikeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0 * * * *")
    public void writeBackLikeCache() {
        final Set<String> likeKeys = redisTemplate.keys(LIKE_KEY_PREFIX);
        if (Objects.isNull(likeKeys)) {
            return;
        }

        final Set<Long> tripIds = extractTripIdsInRedisKeys(likeKeys);
        likeRepository.deleteByTripIds(tripIds);

        final List<Likes> likes = extractLikesInRedisValues(tripIds);
        customLikeRepository.saveAll(likes);
    }

    private Set<Long> extractTripIdsInRedisKeys(final Set<String> likeKeys) {
        return likeKeys.stream().map(key -> {
            final int indexOfColon = key.indexOf(KEY_SEPARATOR);
            return Long.valueOf(key.substring(indexOfColon + 1));
        }).collect(Collectors.toSet());
    }

    private List<Likes> extractLikesInRedisValues(final Set<Long> tripIds) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return tripIds.stream()
                .flatMap(tripId -> {
                    final String key = generateLikeKey(tripId);
                    final Set<Object> memberIds = opsForSet.members(key);
                    return Optional.ofNullable(memberIds)
                            .map(ids -> ids.stream()
                                    .filter(memberId -> !EMPTY_MARKER.equals(memberId))
                                    .map(memberId -> new Likes(tripId, (Long) memberId))
                            ).orElseGet(Stream::empty);
                }).toList();
    }
}
