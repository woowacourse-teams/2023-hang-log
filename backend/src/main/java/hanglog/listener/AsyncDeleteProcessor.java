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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsyncDeleteProcessor {

    private final PlaceRepository placeRepository;
    private final ExpenseRepository expenseRepository;
    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final DayLogRepository dayLogRepository;
    private final TripCityRepository tripCityRepository;
    private final TripRepository tripRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Async
    public void deleteTrips(final Long memberId) {
        tripRepository.deleteByMemberId(memberId);
    }

    @Async
    public void deleteTripCitesByTripId(final Long tripId) {
        tripCityRepository.deleteAllByTripId(tripId);
    }

    @Async
    public void deleteTripCitesByTripIds(final List<Long> tripIds) {
        tripCityRepository.deleteAllByTripIds(tripIds);
    }

    @Async
    public void deleteDayLogs(final List<Long> dayLogIds) {
        dayLogRepository.deleteByIds(dayLogIds);
    }

    @Async
    public void deletePlaces(final List<ItemElement> itemElements) {
        final List<Long> placeIds = itemElements.stream()
                .map(ItemElement::getPlaceId)
                .toList();
        placeRepository.deleteByIds(placeIds);
    }

    @Async
    public void deleteExpenses(final List<ItemElement> itemElements) {
        final List<Long> expenseIds = itemElements.stream()
                .map(ItemElement::getExpenseId)
                .toList();
        expenseRepository.deleteByIds(expenseIds);
    }

    @Async
    public void deleteItems(final List<Long> itemIds) {
        itemRepository.deleteByIds(itemIds);
    }

    @Async
    public void deleteImages(final List<Long> itemIds) {
        imageRepository.deleteByItemIds(itemIds);
    }

    @Async
    public void deleteRefreshTokens(final Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
