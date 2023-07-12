package hanglog.trip.service;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.presentation.dto.request.TripRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public Long save(final TripRequest tripRequest) {
        // TODO: 도시 id로 이름 가져와 타이틀 생성
        final Trip trip = new Trip("title", tripRequest.getStartDate(), tripRequest.getEndDate());
        return tripRepository.save(trip).getId();
    }
}
