package hanglog.trip.service;

import static org.assertj.core.api.Assertions.assertThat;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.fixture.TripFixture;
import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DayLogServiceTest {

    @InjectMocks
    private DayLogService dayLogService;

    @Mock
    private DayLogRepository dayLogRepository;

    @DisplayName("날짜별 여행을 조회할 수 있다.")
    @Test
    void getDayLogById() {
        // given
        final DayLog dayLog = new DayLog(1L, "런던 여행", 1, TripFixture.LONDON_TRIP, List.of());
        final DayLogGetResponse expected = new DayLogGetResponse(1L, "런던 여행", 1, List.of());

        BDDMockito.given(dayLogRepository.findById(1L))
                .willReturn(Optional.of(dayLog));

        // when
        final DayLogGetResponse actual = dayLogService.getById(dayLog.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
