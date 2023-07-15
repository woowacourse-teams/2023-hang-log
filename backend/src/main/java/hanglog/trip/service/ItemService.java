package hanglog.trip.service;

import hanglog.category.Category;
import hanglog.category.repository.CategoryRepository;
import hanglog.expense.Expense;
import hanglog.global.type.StatusType;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.type.ItemType;
import hanglog.trip.presentation.dto.request.ExpenseRequest;
import hanglog.trip.presentation.dto.request.ItemRequest;
import hanglog.trip.presentation.dto.request.PlaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final DayLogRepository dayLogRepository;

    public Long save(final Long tripId, final ItemRequest itemRequest) {
        // TODO: 유저 인가 로직 필요
        DayLog dayLog = dayLogRepository.findById(itemRequest.getDayLogId())
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));

        Item item = new Item(
                ItemType.getItemTypeByIsSpot(itemRequest.getItemType()),
                itemRequest.getTitle(),
                getNewItemOrdinal(tripId),
                itemRequest.getRating(),
                itemRequest.getMemo(),
                getPlaceByItemRequest(itemRequest),
                dayLog,
                getExpenseByItemRequest(itemRequest)
        );
        validateAlreadyDeleted(item);
        return itemRepository.save(item).getId();
    }

    public void update(final Long tripId, final Long itemId, final ItemRequest itemRequest) {
        DayLog dayLog = dayLogRepository.findById(itemRequest.getDayLogId())
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 여행 아이템이 존재하지 않습니다."));
        validateAlreadyDeleted(item);

        Item updateditem = new Item(
                itemId,
                ItemType.getItemTypeByIsSpot(itemRequest.getItemType()),
                itemRequest.getTitle(),
                item.getOrdinal(),
                itemRequest.getRating(),
                itemRequest.getMemo(),
                getPlaceByItemRequest(itemRequest),
                dayLog,
                getExpenseByItemRequest(itemRequest)
        );
        itemRepository.save(updateditem);
    }

    private Place getPlaceByItemRequest(final ItemRequest itemRequest) {
        if (itemRequest.getPlace() == null) {
            return null;
        }
        return createPlaceByPlaceRequest(itemRequest.getPlace());
    }

    private Place createPlaceByPlaceRequest(final PlaceRequest placeRequest) {
        Category category = categoryRepository.findByGoogleApiId(placeRequest.getCategoryApiId())
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 카테고리가 존재하지 않습니다."));

        return new Place(
                placeRequest.getName(),
                placeRequest.getAddress(),
                placeRequest.getLatitude(),
                placeRequest.getLongitude(),
                category);
    }

    private Expense getExpenseByItemRequest(final ItemRequest itemRequest) {
        if (itemRequest.getExpense() == null) {
            return null;
        }
        return createExpenseByExpenseRequest(itemRequest.getExpense());
    }

    private Expense createExpenseByExpenseRequest(final ExpenseRequest expenseRequest) {
        final Category expenseCategory = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 카테고리가 존재하지 않습니다."));
        return new Expense(
                expenseRequest.getCurrency(),
                expenseRequest.getAmount(),
                expenseCategory
        );
    }

    private int getNewItemOrdinal(final Long dayLogId) {
        return dayLogRepository.findById(dayLogId)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."))
                .getItems()
                .size() + 1;
    }

    public void delete(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 여행 아이템이 존재하지 않습니다."));
        validateAlreadyDeleted(item);
        Item deletedItem = new Item(
                item.getId(),
                item.getItemType(),
                item.getTitle(),
                item.getOrdinal(),
                item.getRating(),
                item.getMemo(),
                item.getPlace(),
                item.getDayLog(),
                item.getExpense(),
                StatusType.DELETED
        );
        itemRepository.save(deletedItem);
    }

    private void validateAlreadyDeleted(final Item item) {
        if (item.getStatus().equals(StatusType.DELETED)) {
            throw new IllegalArgumentException("이미 삭제된 여행 아이템입니다.");
        }
    }
}
