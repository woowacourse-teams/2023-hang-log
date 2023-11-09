package hanglog.listener;

import hanglog.member.domain.MemberDeleteEvent;
import hanglog.trip.domain.TripDeleteEvent;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.dto.ItemElement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeleteEventListener {

    private final CustomDayLogRepository customDayLogRepository;
    private final CustomItemRepository customItemRepository;
    private final TransactionalDeleteProcessor transactionalDeleteProcessor;

    @Async
    @Transactional
    @TransactionalEventListener
    public void deleteMember(final MemberDeleteEvent event) {
        final List<Long> dayLogIds = customDayLogRepository.findDayLogIdsByTripIds(event.getTripIds());
        transactionalDeleteProcessor.deleteRefreshTokens(event.getMemberId());
        transactionalDeleteProcessor.deleteTrips(event.getMemberId());
        transactionalDeleteProcessor.deleteTripCitesByTripIds(event.getTripIds());
        deleteTripElements(dayLogIds);
    }

    @Async
    @Transactional
    @TransactionalEventListener
    public void deleteTrip(final TripDeleteEvent event) {
        final List<Long> dayLogIds = customDayLogRepository.findDayLogIdsByTripId(event.getTripId());
        transactionalDeleteProcessor.deleteTripCitesByTripId(event.getTripId());
        deleteTripElements(dayLogIds);
    }

    private void deleteTripElements(final List<Long> dayLogIds) {
        final List<ItemElement> itemElements = customItemRepository.findItemIdsByDayLogIds(dayLogIds);
        final List<Long> itemIds = itemElements.stream()
                .map(ItemElement::getItemId)
                .toList();

        transactionalDeleteProcessor.deleteDayLogs(dayLogIds);
        transactionalDeleteProcessor.deleteItems(itemIds);
        transactionalDeleteProcessor.deletePlaces(itemElements);
        transactionalDeleteProcessor.deleteExpenses(itemElements);
        transactionalDeleteProcessor.deleteImages(itemIds);
    }
}
