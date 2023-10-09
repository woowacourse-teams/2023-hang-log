package hanglog.member.event;

import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.expense.domain.repository.ExpenseRepository;
import hanglog.image.domain.repository.ImageRepository;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.domain.repository.CustomTripRepository;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.repository.PlaceRepository;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.ItemElement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeleteEventListener {

    private final CustomTripRepository customTripRepository;
    private final CustomDayLogRepository customDayLogRepository;
    private final CustomItemRepository customItemRepository;
    private final PlaceRepository placeRepository;
    private final ExpenseRepository expenseRepository;
    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final DayLogRepository dayLogRepository;
    private final SharedTripRepository sharedTripRepository;
    private final PublishedTripRepository publishedTripRepository;
    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @EventListener
    public void delete(final MemberDeleteEvent event) {
        final List<Long> tripIds = customTripRepository.findTripIdsByMemberId(event.getMemberId());
        final List<Long> dayLogIds = customDayLogRepository.findDayLogIdsByTripIds(tripIds);
        List<ItemElement> itemElements = customItemRepository.findItemIdsByDayLogIds(dayLogIds);

        final List<Long> placeIds = itemElements.stream()
                .map(ItemElement::getPlaceId)
                .toList();

        placeRepository.deleteByIds(placeIds);

        final List<Long> expenseIds = itemElements.stream()
                .map(ItemElement::getExpenseId)
                .toList();

        expenseRepository.deleteByIds(expenseIds);

        final List<Long> itemIds = itemElements.stream()
                .map(ItemElement::getItemId)
                .toList();

        imageRepository.deleteByItemIds(itemIds);

        itemRepository.deleteByIds(itemIds);

        dayLogRepository.deleteByIds(dayLogIds);

        sharedTripRepository.deleteByTripIds(tripIds);

        publishedTripRepository.deleteByTripIds(tripIds);

        tripRepository.deleteByMemberId(event.getMemberId());

        refreshTokenRepository.deleteByMemberId(event.getMemberId());

        memberRepository.deleteByMemberId(event.getMemberId());
    }
}
