package hanglog.trip.service;

import hanglog.auth.domain.Accessor;
import hanglog.city.domain.City;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.LikesRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.CommunitySingleTripResponse;
import hanglog.trip.dto.response.CommunityTripsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {

    private final LikesRepository likesRepository;
    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;

    public CommunityTripsResponse getTripsByPage(final Accessor accessor, final Pageable pageable) {
        final List<Trip> trips = tripRepository.findPublishedTripByPageable(pageable.previousOrFirst());
        final List<CommunitySingleTripResponse> communitySingleTripResponses = trips.stream()
                .map(trip -> getTripResponse(accessor, trip))
                .toList();
        final Long lastPageIndex = tripRepository.countPublishedTrip() / pageable.getPageSize() + 1;
        return new CommunityTripsResponse(communitySingleTripResponses, lastPageIndex);
    }

    private CommunitySingleTripResponse getTripResponse(final Accessor accessor, final Trip trip) {
        final List<City> cities = getCitiesByTripId(trip.getId());
        final Long likeCount = likesRepository.countLikesByTrip(trip);

        if (accessor.isMember()) {
            final boolean isLike = likesRepository.existsByMemberIdAndTrip(accessor.getMemberId(), trip);
            return CommunitySingleTripResponse.of(trip, cities, isLike, likeCount);
        }
        return CommunitySingleTripResponse.of(trip, cities, false, likeCount);
    }

    private List<City> getCitiesByTripId(final Long tripId) {
        return tripCityRepository.findByTripId(tripId).stream()
                .map(TripCity::getCity)
                .toList();
    }
}
