package hanglog.community.service;

import static hanglog.community.domain.recommendstrategy.RecommendType.LIKE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
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
import hanglog.like.domain.repository.LikeRepository;
import hanglog.like.dto.LikeElement;
import hanglog.like.dto.LikeElements;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.TripCityElements;
import hanglog.trip.dto.response.TripDetailResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    private final LikeRepository likeRepository;
    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;
    private final CityRepository cityRepository;
    private final RecommendStrategies recommendStrategies;
    private final PublishedTripRepository publishedTripRepository;
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

        final LikeElements likeElements = new LikeElements(getLikeElements(accessor.getMemberId(), tripIds));
        final Map<Long, LikeInfo> likeInfoByTrip = likeElements.toLikeMap();

        return trips.stream()
                .map(trip -> CommunityTripResponse.of(
                        trip,
                        citiesByTrip.get(trip.getId()),
                        isLike(likeInfoByTrip, trip.getId()),
                        getLikeCount(likeInfoByTrip, trip.getId())
                )).toList();
    }


    private boolean isLike(final Map<Long, LikeInfo> likeInfoByTrip, final Long tripId) {
        final LikeInfo likeInfo = likeInfoByTrip.get(tripId);
        if (likeInfo == null) {
            return false;
        }
        return likeInfo.isLike();
    }

    private Long getLikeCount(final Map<Long, LikeInfo> likeInfoByTrip, final Long tripId) {
        final LikeInfo likeInfo = likeInfoByTrip.get(tripId);
        if (likeInfo == null) {
            return 0L;
        }
        return likeInfo.getLikeCount();
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

        final LikeElement likeElement = getLikeElement(accessor.getMemberId(), tripId);
        final Boolean isWriter = trip.isWriter(accessor.getMemberId());

        return TripDetailResponse.publishedTrip(
                trip,
                cities,
                isWriter,
                likeElement.isLike(),
                likeElement.getLikeCount(),
                publishedDate
        );
    }

    private List<LikeElement> getLikeElements(final Long memberId, final List<Long> tripIds) {
        return tripIds.stream()
                .map(tripId -> getLikeElement(memberId, tripId))
                .toList();
    }

    private LikeElement getLikeElement(final Long memberId, final Long tripId) {
        final SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        final String key = "likes:" + tripId;
        if (TRUE.equals(redisTemplate.hasKey(key))) {
            final boolean isLike = TRUE.equals(opsForSet.isMember(key, memberId));
            final long count = Objects.requireNonNull(opsForSet.size(key)) - 1;
            return new LikeElement(tripId, count, isLike);
        }
        final LikeElement likeElement = likeRepository.findLikeCountAndIsLikeByTripId(memberId, tripId)
                .orElse(new LikeElement(tripId, 0, false));
        cachingLike(key, tripId, opsForSet);
        return likeElement;
    }

    private void cachingLike(final String key, final Long tripId, final SetOperations<String, Object> opsForSet) {
        final List<Long> memberIds = likeRepository.findByTripId(tripId);
        opsForSet.add(key, "empty");
        memberIds.forEach(memberId -> opsForSet.add(key, memberId));
        redisTemplate.expire(key, Duration.ofMinutes(90L));
    }
}
