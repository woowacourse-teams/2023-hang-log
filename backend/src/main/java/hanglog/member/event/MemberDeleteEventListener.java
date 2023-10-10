package hanglog.member.event;

import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.expense.domain.repository.ExpenseRepository;
import hanglog.image.domain.repository.ImageRepository;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.repository.PlaceRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.ItemElement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberDeleteEventListener {

    private final CustomDayLogRepository customDayLogRepository;
    private final CustomItemRepository customItemRepository;
    private final PlaceRepository placeRepository;
    private final ExpenseRepository expenseRepository;
    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final DayLogRepository dayLogRepository;
    private final TripRepository tripRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void delete(final MemberDeleteEvent event) {
        final List<Long> dayLogIds = customDayLogRepository.findDayLogIdsByTripIds(event.getTripIds());
        final List<ItemElement> itemElements = customItemRepository.findItemIdsByDayLogIds(dayLogIds);

        deletePlaces(itemElements);
        deleteExpenses(itemElements);
        deleteImageAndItems(itemElements);

        dayLogRepository.deleteByIds(dayLogIds);
        tripRepository.deleteByMemberId(event.getMemberId());
        refreshTokenRepository.deleteByMemberId(event.getMemberId());
    }

    private void deletePlaces(final List<ItemElement> itemElements) {
        final List<Long> placeIds = itemElements.stream()
                .map(ItemElement::getPlaceId)
                .toList();

        placeRepository.deleteByIds(placeIds);
    }

    private void deleteExpenses(final List<ItemElement> itemElements) {
        final List<Long> expenseIds = itemElements.stream()
                .map(ItemElement::getExpenseId)
                .toList();

        expenseRepository.deleteByIds(expenseIds);
    }

    private void deleteImageAndItems(final List<ItemElement> itemElements) {
        final List<Long> itemIds = itemElements.stream()
                .map(ItemElement::getItemId)
                .toList();

        imageRepository.deleteByItemIds(itemIds);
        itemRepository.deleteByIds(itemIds);
    }
}
