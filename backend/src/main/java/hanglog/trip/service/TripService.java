package hanglog.trip.service;

import hanglog.trip.domain.City;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    public static final String TITLE_POSTFIX = " 여행";

    private final TripRepository tripRepository;
    private final CityRepository cityRepository;
    private final TripCityRepository tripCityRepository;

    public Long save(final TripCreateRequest tripCreateRequest) {
        final List<City> cites = tripCreateRequest.getCityIds().stream()
                .map(cityId -> cityRepository.findById(cityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당하는 도시가 존재하지 않습니다.")))
                .toList();

        final Trip trip = new Trip(
                getInitTitle(cites),
                tripCreateRequest.getStartDate(),
                tripCreateRequest.getEndDate()
        );
        final Trip savedTrip = tripRepository.save(trip);
        saveTripCity(cites, trip);
        return savedTrip.getId();
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

    private String getInitTitle(final List<City> cites) {
        return cites.get(0).getName() + TITLE_POSTFIX;
    }

    private void saveTripCity(final List<City> cites, final Trip trip) {
        for (final City city : cites) {
            tripCityRepository.save(new TripCity(trip, city));
        }
    }

    private void validateAlreadyDeleted(final Trip target) {
        if (target.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 여행입니다.");
        }
    }
}
