package hanglog.community.service;

import static hanglog.community.domain.recommendstrategy.RecommendType.LIKE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.trip.domain.type.PublishedStatusType.PUBLISHED;

import hanglog.auth.domain.Accessor;
import hanglog.city.domain.City;
import hanglog.community.domain.recommendstrategy.RecommendStrategies;
import hanglog.community.domain.recommendstrategy.RecommendStrategy;
import hanglog.community.domain.repository.LikeRepository;
import hanglog.community.dto.response.CommunityTripListResponse;
import hanglog.community.dto.response.CommunityTripResponse;
import hanglog.community.dto.response.RecommendTripListResponse;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.TripDetailResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private static final int RECOMMEND_AMOUNT = 5;

    private final LikeRepository likeRepository;
    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;
    private final RecommendStrategies recommendStrategies;
    private final PublishedTripRepository publishedTripRepository;

    public CommunityTripListResponse getTripsByPage(final Accessor accessor, final Pageable pageable) {
        final List<Trip> trips = tripRepository.findPublishedTripByPageable(pageable.previousOrFirst());
        final List<CommunityTripResponse> communityTripResponse = trips.stream()
                .map(trip -> getTripResponse(accessor, trip))
                .toList();
        final Long lastPageIndex = getLastPageIndex(pageable.getPageSize());

        return new CommunityTripListResponse(communityTripResponse, lastPageIndex);
    }

    private CommunityTripResponse getTripResponse(final Accessor accessor, final Trip trip) {
        final List<City> cities = getCitiesByTripId(trip.getId());
        final Long likeCount = likeRepository.countLikesByTripId(trip.getId());
        if (accessor.isMember()) {
            final boolean isLike = likeRepository.existsByMemberIdAndTripId(accessor.getMemberId(), trip.getId());
            return CommunityTripResponse.of(trip, cities, isLike, likeCount);
        }
        return CommunityTripResponse.of(trip, cities, false, likeCount);
    }

    private List<City> getCitiesByTripId(final Long tripId) {
        return tripCityRepository.findByTripId(tripId).stream()
                .map(TripCity::getCity)
                .toList();
    }

    private Long getLastPageIndex(final int pageSize) {
        final Long totalTripCount = tripRepository.countTripByPublishedStatus(PUBLISHED);
        final Long lastPageIndex = totalTripCount / pageSize;
        if (totalTripCount % pageSize == 0) {
            return lastPageIndex;
        }
        return lastPageIndex + 1;
    }

    public RecommendTripListResponse getRecommendTrips(final Accessor accessor) {
        final RecommendStrategy recommendStrategy = recommendStrategies.mapByRecommendType(LIKE);
        final Pageable pageable = Pageable.ofSize(RECOMMEND_AMOUNT);
        final List<Trip> trips = recommendStrategy.recommend(pageable);

        final List<CommunityTripResponse> communityTripResponses = trips.stream()
                .map(trip -> getTripResponse(accessor, trip))
                .toList();

        return new RecommendTripListResponse(recommendStrategy.getTitle(), communityTripResponses);
    }

    public TripDetailResponse getTripDetail(final Accessor accessor, final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = getCitiesByTripId(tripId);
        final LocalDateTime publishedDate = publishedTripRepository.findByTripId(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID))
                .getCreatedAt();
        final Long likeCount = likeRepository.countLikesByTripId(tripId);
        if (!accessor.isMember()) {
            return TripDetailResponse.publishedTrip(
                    trip,
                    cities,
                    false,
                    false,
                    likeCount,
                    publishedDate
            );
        }
        final Boolean isWriter = trip.isWriter(accessor.getMemberId());
        final Boolean isLike = likeRepository.existsByMemberIdAndTripId(accessor.getMemberId(), tripId);
        return TripDetailResponse.publishedTrip(
                trip,
                cities,
                isWriter,
                isLike,
                likeCount,
                publishedDate
        );
    }
}
