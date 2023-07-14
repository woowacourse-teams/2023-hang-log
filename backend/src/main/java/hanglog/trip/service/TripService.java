package hanglog.trip.service;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    private final TripRepository tripRepository;

    public Long save(final TripCreateRequest tripCreateRequest) {
        // TODO: 도시 id로 이름 가져와 타이틀 생성
        // TODO: TripCity 테이블에 데이터 추가
        final Trip trip = new Trip("title", tripCreateRequest.getStartDate(), tripCreateRequest.getEndDate());
        return tripRepository.save(trip).getId();
    }

    public void update(final Long tripId, final TripUpdateRequest updateRequest) {
        final Trip target = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalStateException("해당하는 여행이 존재하지 않습니다."));
        validateAlreadyDeleted(target);
        if (target.getStartDate() != updateRequest.getStartDate()
                || target.getEndDate() != updateRequest.getEndDate()) {
            // TODO: 일정 변경 기능 -> 메서드 분리 예정
        }

        final Long targetId = target.getId();
        final Trip updatedTrip = new Trip(
                targetId,
                updateRequest.getTitle(),
                updateRequest.getStartDate(),
                updateRequest.getEndDate(),
                updateRequest.getDescription()
        );
        tripRepository.save(updatedTrip);
    }

    public void delete(final Long tripId) {
        final Trip target = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalStateException("해당하는 여행이 존재하지 않습니다."));
        validateAlreadyDeleted(target);
        target.changeStatusToDeleted();
        tripRepository.save(target);
    }

    private void validateAlreadyDeleted(final Trip target) {
        if (target.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 여행입니다.");
        }
    }
}
