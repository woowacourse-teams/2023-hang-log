package hanglog.trip.service;


import static hanglog.global.exception.ExceptionCode.ALREADY_DELETED_DATE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_DAY_LOG_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogGetResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DayLogService {

    private final DayLogRepository dayLogRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public DayLogGetResponse getById(final Long id) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        validateAlreadyDeleted(dayLog);

        return DayLogGetResponse.of(dayLog);
    }

    public void updateTitle(final Long id, final DayLogUpdateTitleRequest request) {
        final DayLog dayLog = dayLogRepository.findById(id)
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

    public void updateOrdinalOfDayLogItems(final Long dayLogId,
                                           final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest) {
        final DayLog dayLog = dayLogRepository.findById(dayLogId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        final List<Item> items = dayLog.getItems();

        // TODO: orders가 item들 수만큼 있는지 valid - custom exeption 추가 필요
        if (itemsOrdinalUpdateRequest.getItemIds().size() != items.size()) {
            throw new IllegalArgumentException("데이로그에 있는 모든 아이템들에 대한 아이디 리스트가 필요합니다.");
        }

        changeOrdinalOfItemsByOrderedItemIds(itemsOrdinalUpdateRequest.getItemIds());
    }

    private void changeOrdinalOfItemsByOrderedItemIds(final List<Long> orderedItemIds) {
        int ordinal = 1;

        for (final Long itemId : orderedItemIds) {
            final Item item = itemRepository.findById(itemId).get();
            item.changeOrdinal(ordinal++);
        }
    }
}
