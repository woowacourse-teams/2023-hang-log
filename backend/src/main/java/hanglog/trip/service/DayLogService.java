package hanglog.trip.service;


import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogGetResponse;
import jakarta.persistence.EntityManagerFactory;
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
    private final EntityManagerFactory entityManagerFactory;

    @Transactional(readOnly = true)
    public DayLogGetResponse getById(final Long id) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));
        validateAlreadyDeleted(dayLog);

        return DayLogGetResponse.of(dayLog);
    }

    public void updateTitle(final Long id, final DayLogUpdateTitleRequest request) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));
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
            throw new IllegalStateException("이미 삭제된 날짜입니다.");
        }
    }

    public void updateOrdinalOfDayLogItems(final Long dayLogId, final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest) {
        // TODO: noexist custom exception 적용 필요
        final DayLog dayLog = dayLogRepository.findById(dayLogId).get();
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
