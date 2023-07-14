package hanglog.trip.service;

import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static hanglog.trip.fixture.TripFixture.UPDATED_LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @DisplayName("새롭게 생성한 여행의 id를 반환한다.")
    @Test
    void save() {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L));

        given(tripRepository.save(any(Trip.class)))
                .willReturn(LONDON_TRIP);

        // when
        final Long actualId = tripService.save(tripCreateRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }

    @DisplayName("update 호출 시 id를 검증하고 save 메서드를 호출한다.")
    @Test
    void update() {
        // given
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        given(tripRepository.findById(UPDATED_LONDON_TRIP.getId()))
                .willReturn(Optional.of(UPDATED_LONDON_TRIP));
        given(tripRepository.save(any(Trip.class)))
                .willReturn(UPDATED_LONDON_TRIP);

        // when
        tripService.update(UPDATED_LONDON_TRIP.getId(), updateRequest);

        // then
        verify(tripRepository).findById(UPDATED_LONDON_TRIP.getId());
        verify(tripRepository).save(any(Trip.class));
    }

    @DisplayName("update를 원하는 id가 아닐 시 예외 처리한다.")
    @Test
    void update_IncorrectId() {
        // given
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        given(tripRepository.findById(UPDATED_LONDON_TRIP.getId()))
                .willThrow(IllegalStateException.class);

        // when & then
        assertThatThrownBy(() -> tripService.update(UPDATED_LONDON_TRIP.getId(), updateRequest))
                .isInstanceOf(IllegalStateException.class);
    }


}
