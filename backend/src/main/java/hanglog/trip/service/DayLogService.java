package hanglog.trip.service;


import static hanglog.global.exception.ExceptionCode.ALREADY_DELETED_DATE;
import static hanglog.global.exception.ExceptionCode.INVALID_ORDERED_ITEM_IDS;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_DAY_LOG_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DayLogService {

    private final DayLogRepository dayLogRepository;
    private final CustomItemRepository customItemRepository;

    @Transactional(readOnly = true)
    public DayLogResponse getById(final Long id) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        validateAlreadyDeleted(dayLog);

        return DayLogResponse.of(dayLog);
    }

    public void updateTitle(final Long id, final DayLogUpdateTitleRequest request) {
        final DayLog dayLog = dayLogRepository.findWithItemsById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        validateAlreadyDeleted(dayLog);

        final DayLog updatedDayLog = new DayLog(
                dayLog.getId(),
                request.getTitle(),
                dayLog.getOrdinal(),
                dayLog.getTrip(),
                dayLog.getItems()
        );
        dayLogRepository.save(updatedDayLog);
    }

    private void validateAlreadyDeleted(final DayLog dayLog) {
        if (dayLog.isDeleted()) {
            throw new BadRequestException(ALREADY_DELETED_DATE);
        }
    }

    public void updateOrdinalOfItems(final Long dayLogId, final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest) {
        final DayLog dayLog = dayLogRepository.findWithItemsById(dayLogId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        final List<Item> items = dayLog.getItems();

        final List<Long> orderedItemIds = itemsOrdinalUpdateRequest.getItemIds();
        validateOrderedItemIds(items, orderedItemIds);
        customItemRepository.updateOrdinals(orderedItemIds);
    }

    private void validateOrderedItemIds(final List<Item> items, final List<Long> orderedItemIds) {
        final Set<Long> itemIds = items.stream()
                .map(Item::getId)
                .collect(Collectors.toSet());
        final Set<Long> orderedItemIdsSet = new HashSet<>(orderedItemIds);

        if (!itemIds.equals(orderedItemIdsSet)) {
            throw new BadRequestException(INVALID_ORDERED_ITEM_IDS);
        }
    }
}
