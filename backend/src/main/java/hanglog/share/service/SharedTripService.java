package hanglog.share.service;

import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_SHARED_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.global.exception.BadRequestException;
import hanglog.share.domain.SharedTrip;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.share.dto.response.SharedTripCodeResponse;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SharedTripService {

    private final SharedTripRepository sharedTripRepository;
    private final TripRepository tripRepository;

    @Transactional(readOnly = true)
    public Long getTripId(final String sharedCode) {
        final SharedTrip sharedTrip = sharedTripRepository.findBySharedCode(sharedCode)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_SHARED_CODE));

        if (sharedTrip.isUnShared()) {
            throw new BadRequestException(INVALID_SHARE_CODE);
        }

        return sharedTrip.getTrip().getId();
    }

    public SharedTripCodeResponse updateSharedTripStatus(
            final Long tripId,
            final SharedTripStatusRequest sharedTripStatusRequest
    ) {
        final Trip trip = tripRepository.findTripByIdWithFetch(tripId)
                .orElse(findTripWithNoFetch(tripId));

        final SharedTrip sharedTrip = Optional.ofNullable(trip.getSharedTrip())
                .orElseGet(() -> SharedTrip.createdBy(trip));

        sharedTrip.updateSharedStatus(sharedTripStatusRequest.getSharedStatus());
        sharedTripRepository.save(sharedTrip);
        return SharedTripCodeResponse.of(sharedTrip);
    }

    private Trip findTripWithNoFetch(final Long tripId){
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
    }
}
