package hanglog.trip.service;

import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_1;
import static hanglog.trip.fixture.DayLogFixture.UPDATED_LONDON_DAYLOG;
import static hanglog.trip.fixture.ItemFixture.DAYLOG_FOR_ITEM_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogResponse;
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

    @Mock
    private ItemRepository itemRepository;

    @DisplayName("날짜별 여행을 조회할 수 있다.")
    @Test
    void getDayLogById() {
        // given
        final DayLogResponse expected = new DayLogResponse(
                1L,
                "런던 여행 1일차",
                1,
                LocalDate.of(2023, 7, 1),
                List.of());

        given(dayLogRepository.findById(1L))
                .willReturn(Optional.of(LONDON_DAYLOG_1));

        // when
        final DayLogResponse actual = dayLogService.getById(LONDON_DAYLOG_1.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("날짜별 제목을 수정한다.")
    @Test
    void updateTitle() {
        // given
        final DayLogUpdateTitleRequest request = new DayLogUpdateTitleRequest("updated");

        given(dayLogRepository.findById(1L))
                .willReturn(Optional.of(LONDON_DAYLOG_1));
        given(dayLogRepository.save(any(DayLog.class)))
                .willReturn(UPDATED_LONDON_DAYLOG);

        // when
        dayLogService.updateTitle(LONDON_DAYLOG_1.getId(), request);

        // then
        verify(dayLogRepository).findById(UPDATED_LONDON_DAYLOG.getId());
        verify(dayLogRepository).save(any(DayLog.class));
    }

    @DisplayName("정렬된 아이템 ID 리스트를 받아 아이템들의 순서를 변경한다.")
    @Test
    void updateItemOrdinals() {
        // given
        final ItemsOrdinalUpdateRequest request = new ItemsOrdinalUpdateRequest(List.of(3L, 2L, 1L));
        given(dayLogRepository.findById(1L))
                .willReturn(Optional.of(DAYLOG_FOR_ITEM_FIXTURE));
        given(itemRepository.findById(1L))
                .willReturn(Optional.ofNullable(DAYLOG_FOR_ITEM_FIXTURE.getItems().get(0)));
        given(itemRepository.findById(2L))
                .willReturn(Optional.ofNullable(DAYLOG_FOR_ITEM_FIXTURE.getItems().get(1)));
        given(itemRepository.findById(3L))
                .willReturn(Optional.ofNullable(DAYLOG_FOR_ITEM_FIXTURE.getItems().get(2)));

        // when
        dayLogService.updateOrdinalOfItems(1L, request);

        // then
        final List<Item> items = DAYLOG_FOR_ITEM_FIXTURE.getItems();
        assertSoftly(softly -> {
            softly.assertThat(items.get(0).getOrdinal()).isEqualTo(3);
            softly.assertThat(items.get(1).getOrdinal()).isEqualTo(2);
            softly.assertThat(items.get(2).getOrdinal()).isEqualTo(1);
        });
    }
}
