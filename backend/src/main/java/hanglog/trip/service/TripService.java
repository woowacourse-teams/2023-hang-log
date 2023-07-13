package hanglog.trip.service;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.presentation.dto.request.TripRequest;
import hanglog.trip.presentation.dto.request.TripUpdateRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public Long save(final TripRequest tripRequest) {
        // TODO: 도시 id로 이름 가져와 타이틀 생성
        // TODO: TripCity 테이블에 데이터 추가
        final Trip trip = new Trip("title", tripRequest.getStartDate(), tripRequest.getEndDate());
        return tripRepository.save(trip).getId();
    }

    public void update(final Long tripId, final TripUpdateRequest updateRequest) {
        // TODO: 일정 변경 기능
        final Optional<Trip> target = tripRepository.findById(tripId);
        if (target.isEmpty()) {
            throw new IllegalStateException("해당하는 여행이 존재하지 않습니다.");
        }
        final Long targetId = target.get().getId();
        final Trip updatedTrip = new Trip(
                targetId,
                updateRequest.getTitle(),
                updateRequest.getStartDate(),
                updateRequest.getEndDate(),
                updateRequest.getDescription()
        );
        tripRepository.save(updatedTrip);
    }
}
