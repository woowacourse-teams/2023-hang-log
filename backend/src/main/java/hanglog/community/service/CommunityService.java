package hanglog.community.service;

import static hanglog.community.domain.recommendstrategy.RecommendType.LIKE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.like.domain.LikeRedisConstants.EMPTY_MARKER;
import static hanglog.like.domain.LikeRedisConstants.LIKE_TTL;
import static hanglog.like.domain.LikeRedisConstants.generateLikeKey;
import static hanglog.trip.domain.type.PublishedStatusType.PUBLISHED;
import static java.lang.Boolean.TRUE;

import hanglog.auth.domain.Accessor;
import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.community.domain.recommendstrategy.RecommendStrategies;
import hanglog.community.domain.recommendstrategy.RecommendStrategy;
import hanglog.community.domain.repository.PublishedTripRepository;
import hanglog.community.dto.response.CommunityTripListResponse;
import hanglog.community.dto.response.CommunityTripResponse;
import hanglog.community.dto.response.RecommendTripListResponse;
import hanglog.global.exception.BadRequestException;
import hanglog.like.domain.LikeInfo;
import hanglog.like.domain.repository.CustomLikeRepository;
import hanglog.like.dto.LikeElement;
import hanglog.like.dto.LikeElements;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.TripCityElements;
import hanglog.trip.dto.response.TripDetailResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunityService {

    private static final int RECOMMEND_AMOUNT = 5;

    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;
    private final CityRepository cityRepository;
    private final PublishedTripRepository publishedTripRepository;
    private final CustomLikeRepository customLikeRepository;
    private final RecommendStrategies recommendStrategies;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public CommunityTripListResponse getCommunityTripsByPage(final Accessor accessor, final Pageable pageable) {
        final List<Trip> trips = tripRepository.findPublishedTripByPageable(pageable.previousOrFirst());
        final List<CommunityTripResponse> communityTripResponses = getCommunityTripResponses(accessor, trips);
        final Long lastPageIndex = getLastPageIndex(pageable.getPageSize());
        return new CommunityTripListResponse(communityTripResponses, lastPageIndex);
    }

    @Transactional(readOnly = true)
    public RecommendTripListResponse getRecommendTrips(final Accessor accessor) {
        final RecommendStrategy recommendStrategy = recommendStrategies.mapByRecommendType(LIKE);
        final Pageable pageable = Pageable.ofSize(RECOMMEND_AMOUNT);
        final List<Trip> trips = recommendStrategy.recommend(pageable);
        final List<CommunityTripResponse> communityTripResponses = getCommunityTripResponses(accessor, trips);
        return new RecommendTripListResponse(recommendStrategy.getTitle(), communityTripResponses);
    }

    private List<CommunityTripResponse> getCommunityTripResponses(final Accessor accessor, final List<Trip> trips) {
        final List<Long> tripIds = trips.stream().map(Trip::getId).toList();

        final TripCityElements tripCityElements = new TripCityElements(
                tripCityRepository.findTripIdAndCitiesByTripIds(tripIds)
        );
        final Map<Long, List<City>> citiesByTrip = tripCityElements.toCityMap();
        final Map<Long, LikeInfo> likeInfoByTrip = getLikeInfoByTripIds(accessor.getMemberId(), tripIds);

        return trips.stream()
                .map(trip -> CommunityTripResponse.of(
                        trip,
                        citiesByTrip.get(trip.getId()),
                        likeInfoByTrip.get(trip.getId()).isLike(),
                        likeInfoByTrip.get(trip.getId()).getLikeCount()
                )).toList();
    }

    private Long getLastPageIndex(final int pageSize) {
        final Long totalTripCount = tripRepository.countTripByPublishedStatus(PUBLISHED);
        final long lastPageIndex = totalTripCount / pageSize;
        if (totalTripCount % pageSize == 0) {
            return lastPageIndex;
        }
        return lastPageIndex + 1;
    }

    @Transactional(readOnly = true)
    public TripDetailResponse getTripDetail(final Accessor accessor, final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = cityRepository.findCitiesByTripId(tripId);
        final LocalDateTime publishedDate = publishedTripRepository.findByTripId(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID))
                .getCreatedAt();

        final LikeInfo likeInfo = getLikeInfoByTripId(accessor.getMemberId(), tripId);
        final Boolean isWriter = trip.isWriter(accessor.getMemberId());

        return TripDetailResponse.publishedTrip(
                trip,
                cities,
                isWriter,
                likeInfo.isLike(),
                likeInfo.getLikeCount(),
                publishedDate
        );
    }

    private Map<Long, LikeInfo> getLikeInfoByTripIds(final Long memberId, final List<Long> tripIds) {
        final Map<Long, LikeInfo> likeInfoByTrip = new HashMap<>();

        final List<Long> nonCachedTripIds = new ArrayList<>();
        for (final Long tripId : tripIds) {
            final String key = generateLikeKey(tripId);
            if (TRUE.equals(redisTemplate.hasKey(key))) {
                likeInfoByTrip.put(tripId, readLikeInfoFromCache(key, memberId));
            } else {
                nonCachedTripIds.add(tripId);
            }
        }

        if (!nonCachedTripIds.isEmpty()) {
            final List<LikeElement> likeElementByTripIds = customLikeRepository.findLikeElementByTripIds(nonCachedTripIds);
            likeElementByTripIds.addAll(getEmptyLikeElements(likeElementByTripIds, nonCachedTripIds));
            likeElementByTripIds.forEach(this::cachingLike);
            likeInfoByTrip.putAll(new LikeElements(likeElementByTripIds).toLikeInfo(memberId));
        }
        return likeInfoByTrip;
    }

    private LikeInfo getLikeInfoByTripId(final Long memberId, final Long tripId) {
        final String key = generateLikeKey(tripId);
        if (TRUE.equals(redisTemplate.hasKey(key))) {
            return readLikeInfoFromCache(key, memberId);
        }

        final LikeElement likeElement = customLikeRepository.findLikesElementByTripId(tripId)
                .orElse(LikeElement.empty(tripId));
        cachingLike(likeElement);
        return likeElement.toLikeInfo(memberId);
    }

    private List<LikeElement> getEmptyLikeElements(
            final List<LikeElement> likeElementByTripIds,
            final List<Long> nonCachedTripIds
    ) {
        return nonCachedTripIds.stream()
                .filter(tripId -> doesNotContainTripId(likeElementByTripIds, tripId))
                .map(LikeElement::empty)
                .toList();
    }

    private boolean doesNotContainTripId(final List<LikeElement> likeElementByTripIds, final Long tripId) {
        return likeElementByTripIds.stream()
                .noneMatch(likeElement -> likeElement.getTripId().equals(tripId));
    }

    private LikeInfo readLikeInfoFromCache(final String key, final Long memberId) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        final boolean isLike = TRUE.equals(opsForSet.isMember(key, memberId));
        final long count = Objects.requireNonNull(opsForSet.size(key)) - 1;
        return new LikeInfo(count, isLike);
    }

    private void cachingLike(final LikeElement likeElement) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        final String key = generateLikeKey(likeElement.getTripId());
        opsForSet.add(key, EMPTY_MARKER);
        final Set<Long> memberIds = likeElement.getMemberIds();
        if (!memberIds.isEmpty()) {
            opsForSet.add(key, likeElement.getMemberIds().toArray());
        }
        redisTemplate.expire(key, LIKE_TTL);
    }
}
