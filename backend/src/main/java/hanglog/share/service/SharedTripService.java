package hanglog.share.service;

import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_SHARED_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.global.exception.BadRequestException;
import hanglog.share.domain.SharedTrip;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.share.dto.response.SharedTripCodeResponse;
import hanglog.trip.domain.City;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.TripDetailResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SharedTripService {

    private final SharedTripRepository sharedTripRepository;
    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;

    public TripDetailResponse getTripDetail(final String sharedCode) {
        final SharedTrip sharedTrip = sharedTripRepository.findBySharedCode(sharedCode)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_SHARED_CODE));

        if (sharedTrip.isUnShared()) {
            throw new BadRequestException(INVALID_SHARE_CODE);
        }

        final Long tripId = sharedTrip.getTrip().getId();
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = getCitiesByTripId(tripId);

        return TripDetailResponse.of(trip, cities);
    }

    private List<City> getCitiesByTripId(final Long tripId) {
        return tripCityRepository.findByTripId(tripId).stream()
                .map(TripCity::getCity)
                .toList();
    }

    public SharedTripCodeResponse updateSharedStatus(
            final Long tripId,
            final SharedTripStatusRequest sharedTripStatusRequest
    ) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));

        final SharedTrip sharedTrip = sharedTripRepository.findByTripId(tripId)
                .orElseGet(() -> SharedTrip.createdBy(trip));

        sharedTrip.updateSharedStatus(sharedTripStatusRequest.getSharedStatus());
        sharedTripRepository.save(sharedTrip);
        return SharedTripCodeResponse.of(sharedTrip);
    }
}
