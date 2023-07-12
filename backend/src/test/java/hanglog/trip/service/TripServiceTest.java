package hanglog.trip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.fixture.TripFixture;
import hanglog.trip.presentation.dto.request.TripRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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
        final TripRequest tripRequest = new TripRequest(LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L));

        BDDMockito.given(tripRepository.save(any(Trip.class)))
                .willReturn(TripFixture.LONDON_TRIP);

        // when
        Long actualId = tripService.save(tripRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }
}
