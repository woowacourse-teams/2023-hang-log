package hanglog.community.service;

import static hanglog.community.domain.type.PublishedStatusType.PUBLISHED;

import hanglog.auth.domain.Accessor;
import hanglog.city.domain.City;
import hanglog.community.domain.recommendstrategy.RecommendStrategies;
import hanglog.community.domain.repository.LikesRepository;
import hanglog.community.dto.response.CommunityTripListResponse;
import hanglog.community.dto.response.CommunityTripResponse;
import hanglog.community.dto.response.RecommendTripListResponse;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {

    private static final int RECOMMEND_AMOUNT = 5;

    private final LikesRepository likesRepository;
    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;

    public CommunityTripListResponse getTripsByPage(final Accessor accessor, final Pageable pageable) {
        final List<Trip> trips = tripRepository.findPublishedTripByPageable(pageable.previousOrFirst());
        final List<CommunityTripResponse> communityTripResponse = trips.stream()
                .map(trip -> getTripResponse(accessor, trip))
                .toList();
        final Long lastPageIndex =
                tripRepository.countTripByPublishedStatus(PUBLISHED) / pageable.getPageSize() + 1;
        return new CommunityTripListResponse(communityTripResponse, lastPageIndex);
    }

    private CommunityTripResponse getTripResponse(final Accessor accessor, final Trip trip) {
        final List<City> cities = getCitiesByTripId(trip.getId());
        final Long likeCount = likesRepository.countLikesByTripId(trip.getId());

        if (accessor.isMember()) {
            final boolean isLike = likesRepository.existsByMemberIdAndTripId(trip.getMember().getId(), trip.getId());
            return CommunityTripResponse.of(trip, cities, isLike, likeCount);
        }
        return CommunityTripResponse.of(trip, cities, false, likeCount);
    }

    private List<City> getCitiesByTripId(final Long tripId) {
        return tripCityRepository.findByTripId(tripId).stream()
                .map(TripCity::getCity)
                .toList();
    }

    public RecommendTripListResponse getRecommendTrips(final Accessor accessor) {
        final RecommendStrategies recommendStrategies = RecommendStrategies.generateLikedBased(tripRepository);
        final Pageable pageable = Pageable.ofSize(RECOMMEND_AMOUNT);
        final List<Trip> trips = recommendStrategies.recommend(pageable);

        final List<CommunityTripResponse> communitySingleTripResponses = trips.stream()
                .map(trip -> getTripResponse(accessor, trip))
                .toList();

        return new RecommendTripListResponse(recommendStrategies.getTitle(), communitySingleTripResponses);
    }
}
