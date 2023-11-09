package hanglog.listener;

import hanglog.expense.domain.repository.ExpenseRepository;
import hanglog.login.domain.repository.RefreshTokenRepository;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ImageRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.repository.PlaceRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.ItemElement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransactionalDeleteProcessor {

    private final PlaceRepository placeRepository;
    private final ExpenseRepository expenseRepository;
    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final DayLogRepository dayLogRepository;
    private final TripCityRepository tripCityRepository;
    private final TripRepository tripRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void deleteTrips(final Long memberId) {
        tripRepository.deleteByMemberId(memberId);
    }

    public void deleteTripCitesByTripId(final Long tripId) {
        tripCityRepository.deleteAllByTripId(tripId);
    }

    public void deleteTripCitesByTripIds(final List<Long> tripIds) {
        tripCityRepository.deleteAllByTripIds(tripIds);
    }

    public void deleteDayLogs(final List<Long> dayLogIds) {
        dayLogRepository.deleteByIds(dayLogIds);
    }

    public void deletePlaces(final List<ItemElement> itemElements) {
        final List<Long> placeIds = itemElements.stream()
                .map(ItemElement::getPlaceId)
                .toList();
        placeRepository.deleteByIds(placeIds);
    }

    public void deleteExpenses(final List<ItemElement> itemElements) {
        final List<Long> expenseIds = itemElements.stream()
                .map(ItemElement::getExpenseId)
                .toList();
        expenseRepository.deleteByIds(expenseIds);
    }

    public void deleteItems(final List<Long> itemIds) {
        itemRepository.deleteByIds(itemIds);
    }

    public void deleteImages(final List<Long> itemIds) {
        imageRepository.deleteByItemIds(itemIds);
    }

    public void deleteRefreshTokens(final Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
