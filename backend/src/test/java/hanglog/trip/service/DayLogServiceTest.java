package hanglog.trip.service;

import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG;
import static hanglog.trip.fixture.DayLogFixture.UPDATED_LONDON_DAYLOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.response.DayLogGetResponse;
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
class DayLogServiceTest {

    @InjectMocks
    private DayLogService dayLogService;

    @Mock
    private DayLogRepository dayLogRepository;

    @DisplayName("날짜별 여행을 조회할 수 있다.")
    @Test
    void getDayLogById() {
        // given
        final DayLogGetResponse expected = new DayLogGetResponse(
                1L,
                "런던 여행 첫날",
                1,
                LocalDate.of(2023, 7, 1),
                List.of());

        given(dayLogRepository.findById(1L))
                .willReturn(Optional.of(LONDON_DAYLOG));

        // when
        final DayLogGetResponse actual = dayLogService.getById(LONDON_DAYLOG.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("날짜별 제목을 수정한다.")
    @Test
    void updateTitle() {
        // given
        final DayLogUpdateTitleRequest request = new DayLogUpdateTitleRequest("updated");

        given(dayLogRepository.findById(1L))
                .willReturn(Optional.of(LONDON_DAYLOG));
        given(dayLogRepository.save(any(DayLog.class)))
                .willReturn(UPDATED_LONDON_DAYLOG);

        // when
        dayLogService.updateTitle(LONDON_DAYLOG.getId(), request);

        // then
        verify(dayLogRepository).findById(UPDATED_LONDON_DAYLOG.getId());
        verify(dayLogRepository).save(any(DayLog.class));
    }
}
