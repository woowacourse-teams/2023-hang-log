package hanglog.member.event;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hanglog.listener.TransactionalDeleteProcessor;
import hanglog.listener.DeleteEventListener;
import hanglog.member.domain.MemberDeleteEvent;
import hanglog.trip.domain.TripDeleteEvent;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomItemRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteEventListenerTest {

    @Mock
    private CustomDayLogRepository customDayLogRepository;
    @Mock
    private CustomItemRepository customItemRepository;

    @Mock
    private TransactionalDeleteProcessor transactionalDeleteProcessor;

    @InjectMocks
    private DeleteEventListener listener;

    @DisplayName("deleteMember 메서드에서 올바르게 레포지토리의 메서드를 호출한다.")
    @Test
    void deleteMember() {
        // given
        final MemberDeleteEvent event = new MemberDeleteEvent(List.of(1L, 2L, 3L), 1L);

        when(customDayLogRepository.findDayLogIdsByTripIds(event.getTripIds())).thenReturn(new ArrayList<>());
        doNothing().when(transactionalDeleteProcessor).deleteRefreshTokens(anyLong());
        doNothing().when(transactionalDeleteProcessor).deleteTrips(anyLong());
        doNothing().when(transactionalDeleteProcessor).deleteTripCitesByTripIds(anyList());

        when(customItemRepository.findItemIdsByDayLogIds(anyList())).thenReturn(new ArrayList<>());
        doNothing().when(transactionalDeleteProcessor).deleteDayLogs(anyList());
        doNothing().when(transactionalDeleteProcessor).deleteItems(anyList());
        doNothing().when(transactionalDeleteProcessor).deletePlaces(anyList());
        doNothing().when(transactionalDeleteProcessor).deleteExpenses(anyList());
        doNothing().when(transactionalDeleteProcessor).deleteImages(anyList());

        // when
        listener.deleteMember(event);

        // then
        verify(customDayLogRepository, times(1)).findDayLogIdsByTripIds(event.getTripIds());
        verify(transactionalDeleteProcessor, times(1)).deleteRefreshTokens(anyLong());
        verify(transactionalDeleteProcessor, times(1)).deleteTrips(anyLong());
        verify(transactionalDeleteProcessor, times(1)).deleteTripCitesByTripIds(anyList());

        verify(customItemRepository, times(1)).findItemIdsByDayLogIds(anyList());
        verify(transactionalDeleteProcessor, times(1)).deletePlaces(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteExpenses(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteItems(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteImages(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteDayLogs(anyList());
    }

    @DisplayName("deleteTrip 메서드에서 올바르게 레포지토리의 메서드를 호출한다.")
    @Test
    void deleteTrip() {
        // given
        final TripDeleteEvent event = new TripDeleteEvent(1L);

        when(customDayLogRepository.findDayLogIdsByTripId(event.getTripId())).thenReturn(new ArrayList<>());
        doNothing().when(transactionalDeleteProcessor).deleteTripCitesByTripId(anyLong());

        when(customItemRepository.findItemIdsByDayLogIds(anyList())).thenReturn(new ArrayList<>());
        doNothing().when(transactionalDeleteProcessor).deleteDayLogs(anyList());
        doNothing().when(transactionalDeleteProcessor).deleteItems(anyList());
        doNothing().when(transactionalDeleteProcessor).deletePlaces(anyList());
        doNothing().when(transactionalDeleteProcessor).deleteExpenses(anyList());
        doNothing().when(transactionalDeleteProcessor).deleteImages(anyList());

        // when
        listener.deleteTrip(event);

        // then
        verify(customDayLogRepository, times(1)).findDayLogIdsByTripId(event.getTripId());
        verify(customItemRepository, times(1)).findItemIdsByDayLogIds(anyList());

        verify(customItemRepository, times(1)).findItemIdsByDayLogIds(anyList());
        verify(transactionalDeleteProcessor, times(1)).deletePlaces(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteExpenses(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteItems(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteImages(anyList());
        verify(transactionalDeleteProcessor, times(1)).deleteDayLogs(anyList());
    }
}
