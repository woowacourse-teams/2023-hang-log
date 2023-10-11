package hanglog.member.event;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.expense.domain.repository.ExpenseRepository;
import hanglog.image.domain.repository.ImageRepository;
import hanglog.listener.MemberDeleteEventListener;
import hanglog.member.domain.MemberDeleteEvent;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.repository.PlaceRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberDeleteEventListenerTest {

    @Mock
    private CustomDayLogRepository customDayLogRepository;
    @Mock
    private CustomItemRepository customItemRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private DayLogRepository dayLogRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @InjectMocks
    private MemberDeleteEventListener listener;

    @DisplayName("delete 메서드에서 올바르게 레포지토리의 메서드를 호출한다.")
    @Test
    void delete() {
        // given
        final MemberDeleteEvent event = new MemberDeleteEvent(List.of(1L, 2L, 3L), 1L);

        when(customDayLogRepository.findDayLogIdsByTripIds(event.getTripIds())).thenReturn(new ArrayList<>());
        when(customItemRepository.findItemIdsByDayLogIds(anyList())).thenReturn(new ArrayList<>());
        doNothing().when(placeRepository).deleteByIds(anyList());
        doNothing().when(expenseRepository).deleteByIds(anyList());
        doNothing().when(imageRepository).deleteByItemIds(anyList());
        doNothing().when(itemRepository).deleteByIds(anyList());
        doNothing().when(dayLogRepository).deleteByIds(anyList());
        doNothing().when(tripRepository).deleteByMemberId(anyLong());
        doNothing().when(refreshTokenRepository).deleteByMemberId(anyLong());

        // when
        listener.delete(event);

        // then
        verify(customDayLogRepository, times(1)).findDayLogIdsByTripIds(event.getTripIds());
        verify(customItemRepository, times(1)).findItemIdsByDayLogIds(anyList());
        verify(placeRepository, times(1)).deleteByIds(anyList());
        verify(expenseRepository, times(1)).deleteByIds(anyList());
        verify(imageRepository, times(1)).deleteByItemIds(anyList());
        verify(itemRepository, times(1)).deleteByIds(anyList());
        verify(dayLogRepository, times(1)).deleteByIds(anyList());
        verify(tripRepository, times(1)).deleteByMemberId(anyLong());
        verify(refreshTokenRepository, times(1)).deleteByMemberId(anyLong());
    }
}
