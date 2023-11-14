package hanglog.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

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
public class TripDeleteEventListener {

    private final CustomDayLogRepository customDayLogRepository;
    private final CustomItemRepository customItemRepository;
    private final AsyncDeleteProcessor asyncDeleteProcessor;

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void deleteTrip(final TripDeleteEvent event) {
        final List<Long> dayLogIds = customDayLogRepository.findDayLogIdsByTripId(event.getTripId());
        asyncDeleteProcessor.deleteTripCitesByTripId(event.getTripId());
        deleteTripElements(dayLogIds);
    }

    private void deleteTripElements(final List<Long> dayLogIds) {
        final List<ItemElement> itemElements = customItemRepository.findItemIdsByDayLogIds(dayLogIds);
        final List<Long> itemIds = itemElements.stream()
                .map(ItemElement::getItemId)
                .toList();

        asyncDeleteProcessor.deleteDayLogs(dayLogIds);
        asyncDeleteProcessor.deleteItems(itemIds);
        asyncDeleteProcessor.deletePlaces(itemElements);
        asyncDeleteProcessor.deleteExpenses(itemElements);
        asyncDeleteProcessor.deleteImages(itemIds);
    }
}
