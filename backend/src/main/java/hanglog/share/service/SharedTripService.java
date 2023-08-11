package hanglog.share.service;

import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_SHARED_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.share.domain.type.SharedStatusType.UNSHARED;

import hanglog.global.exception.BadRequestException;
import hanglog.share.domain.SharedTrip;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.share.domain.type.SharedStatusType;
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
@Transactional
public class SharedTripService {

    private final SharedTripRepository sharedTripRepository;
    private final TripRepository tripRepository;
    private final TripCityRepository tripCityRepository;

    public TripDetailResponse getTripDetail(final String sharedCode) {
        final SharedTrip sharedTrip = sharedTripRepository.findByShareCode(sharedCode)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_SHARED_CODE));

        validateSharedStatus(sharedTrip.getSharedStatus());

        final Long tripId = sharedTrip.getTrip().getId();
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = getCitiesByTripId(tripId);

        return TripDetailResponse.of(trip, cities);
    }

    private void validateSharedStatus(final SharedStatusType sharedStatusType) {
        if (sharedStatusType == UNSHARED) {
            throw new BadRequestException(INVALID_SHARE_CODE);
        }
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

        sharedTrip.updateSharedStatus(sharedTripStatusRequest);
        sharedTripRepository.save(sharedTrip);
        return SharedTripCodeResponse.of(sharedTrip);
    }
}
