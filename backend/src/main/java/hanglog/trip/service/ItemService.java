package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.ALREADY_DELETED_TRIP_ITEM;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CATEGORY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_DAY_LOG_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ITEM_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUNT_IMAGE_URL;

import hanglog.category.Category;
import hanglog.category.repository.CategoryRepository;
import hanglog.expense.Expense;
import hanglog.global.exception.BadRequestException;
import hanglog.global.type.StatusType;
import hanglog.image.domain.Image;
import hanglog.image.domain.repository.ImageRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.type.ItemType;
import hanglog.trip.dto.request.ExpenseRequest;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.ItemUpdateRequest;
import hanglog.trip.dto.request.PlaceRequest;
import hanglog.trip.dto.response.ItemResponse;
import java.util.List;
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
    private final ImageRepository imageRepository;

    public Long save(final Long tripId, final ItemRequest itemRequest) {
        // TODO: 유저 인가 로직 필요
        final DayLog dayLog = dayLogRepository.findById(itemRequest.getDayLogId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));

        final Item item = new Item(
                ItemType.getItemTypeByIsSpot(itemRequest.getItemType()),
                itemRequest.getTitle(),
                getNewItemOrdinal(tripId),
                itemRequest.getRating(),
                itemRequest.getMemo(),
                getPlaceByItemRequest(itemRequest.getPlace()),
                dayLog,
                getExpenseByItemRequest(itemRequest.getExpense()),
                getImagesByItemRequest(itemRequest)
        );
        validateAlreadyDeleted(item);
        return itemRepository.save(item).getId();
    }

    private List<Image> getImagesByItemRequest(final ItemRequest itemRequest) {
        return itemRequest.getImageUrls().stream()
                .map(imageUrl -> imageRepository.findByImageUrl(imageUrl)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUNT_IMAGE_URL))
                )
                .toList();
    }

    public void update(final Long tripId, final Long itemId, final ItemUpdateRequest itemUpdateRequest) {
        final DayLog dayLog = dayLogRepository.findById(itemUpdateRequest.getDayLogId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        final Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ITEM_ID));
        validateAlreadyDeleted(item);

        Place updatedPlace = item.getPlace();
        if (itemUpdateRequest.getIsPlaceUpdated()) {
            updatedPlace = getPlaceByItemRequest(itemUpdateRequest.getPlace());
        }

        final Item updatedItem = new Item(
                itemId,
                ItemType.getItemTypeByIsSpot(itemUpdateRequest.getItemType()),
                itemUpdateRequest.getTitle(),
                item.getOrdinal(),
                itemUpdateRequest.getRating(),
                itemUpdateRequest.getMemo(),
                updatedPlace,
                dayLog,
                getExpenseByItemRequest(itemUpdateRequest.getExpense())
        );

        itemRepository.save(updatedItem);
    }

    private Place getPlaceByItemRequest(final PlaceRequest placeRequest) {
        if (placeRequest == null) {
            return null;
        }
        return createPlaceByPlaceRequest(placeRequest);
    }

    private Place createPlaceByPlaceRequest(final PlaceRequest placeRequest) {
        // TODO apiCategory를 가지고 category를 탐색
        final Category category = new Category(1L, "문화", "culture");

        return new Place(
                placeRequest.getName(),
                placeRequest.getLatitude(),
                placeRequest.getLongitude(),
                category
        );
    }

    private Expense getExpenseByItemRequest(final ExpenseRequest expenseRequest) {
        if (expenseRequest == null) {
            return null;
        }
        return createExpenseByExpenseRequest(expenseRequest);
    }

    private Expense createExpenseByExpenseRequest(final ExpenseRequest expenseRequest) {
        final Category expenseCategory = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CATEGORY_ID));
        return new Expense(
                expenseRequest.getCurrency(),
                expenseRequest.getAmount(),
                expenseCategory
        );
    }

    private int getNewItemOrdinal(final Long dayLogId) {
        return dayLogRepository.findById(dayLogId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID))
                .getItems()
                .size() + 1;
    }

    public void delete(final Long itemId) {
        final Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ITEM_ID));
        validateAlreadyDeleted(item);
        itemRepository.delete(item);
    }

    private void validateAlreadyDeleted(final Item item) {
        if (item.getStatus().equals(StatusType.DELETED)) {
            throw new BadRequestException(ALREADY_DELETED_TRIP_ITEM);
        }
    }

    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .toList();
    }
}
