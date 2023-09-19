package hanglog.trip.service;

import hanglog.auth.domain.Accessor;
import hanglog.city.domain.City;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.LikesRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.CommunityTripResponse;
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

    public List<CommunityTripResponse> getTripsByPage(final Accessor accessor, final Pageable pageable) {
        final List<Trip> trips = tripRepository.findPublishedTripByPageable(pageable);
        return trips.stream()
                .map(trip -> getTripResponse(accessor, trip))
                .toList();
    }

    private CommunityTripResponse getTripResponse(final Accessor accessor, final Trip trip) {
        final List<City> cities = getCitiesByTripId(trip.getId());
        final Long likeCount = likesRepository.countLikesByTrip(trip);

        if (accessor.isMember()) {
            final boolean isLike = likesRepository.existsByMemberIdAndTrip(accessor.getMemberId(), trip);
            return CommunityTripResponse.of(trip, cities, isLike, likeCount);
        }
        return CommunityTripResponse.of(trip, cities, false, likeCount);
    }

    private List<City> getCitiesByTripId(final Long tripId) {
        return tripCityRepository.findByTripId(tripId).stream()
                .map(TripCity::getCity)
                .toList();
    }
}
