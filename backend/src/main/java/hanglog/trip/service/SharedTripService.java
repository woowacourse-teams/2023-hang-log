package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_SHARED_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.SharedTrip;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.SharedTripRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.TripDetailResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SharedTripService {

    private final SharedTripRepository sharedTripRepository;
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Long getTripId(final String sharedCode) {
        final SharedTrip sharedTrip = sharedTripRepository.findBySharedCode(sharedCode)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_SHARED_CODE));

        if (!sharedTrip.isShared()) {
            throw new BadRequestException(INVALID_SHARE_CODE);
        }

        return sharedTrip.getTrip().getId();
    }

    @Transactional(readOnly = true)
    public TripDetailResponse getSharedTripDetail(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = cityRepository.findCitiesByTripId(tripId);
        return TripDetailResponse.sharedTrip(trip, cities);
    }
}
