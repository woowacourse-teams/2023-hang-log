package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_ASSOCIATE_DAYLOG_WITH_TRIP;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CATEGORY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_DAY_LOG_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ITEM_ID;
import static hanglog.image.util.ImageUrlConverter.convertUrlToName;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.currency.domain.type.CurrencyType;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;
import hanglog.global.exception.BadRequestException;
import hanglog.image.domain.Image;
import hanglog.image.domain.repository.CustomImageRepository;
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
    private final CustomImageRepository customImageRepository;

    public Long save(final Long tripId, final ItemRequest itemRequest) {
        // TODO: 유저 인가 로직 필요
        final DayLog dayLog = dayLogRepository.findWithItemById(itemRequest.getDayLogId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        validateAssociationTripAndDayLog(tripId, dayLog);

        final List<Image> images = makeImages(itemRequest);
        final Item item = new Item(
                ItemType.getItemTypeByIsSpot(itemRequest.getItemType()),
                itemRequest.getTitle(),
                getNewItemOrdinal(dayLog),
                itemRequest.getRating(),
                itemRequest.getMemo(),
                makePlace(itemRequest.getPlace()),
                dayLog,
                makeExpense(itemRequest.getExpense()),
                images
        );
        final Item savedItem = itemRepository.save(item);
        images.forEach(image -> image.setItem(savedItem));
        customImageRepository.saveAll(images);
        return savedItem.getId();
    }

    private void validateAssociationTripAndDayLog(final Long tripId, final DayLog dayLog) {
        final Long actualTripId = dayLog.getTrip().getId();
        if (!actualTripId.equals(tripId)) {
            throw new BadRequestException(NOT_ASSOCIATE_DAYLOG_WITH_TRIP);
        }
    }

    private List<Image> makeImages(final ItemRequest itemRequest) {
        return itemRequest.getImageUrls().stream()
                .map(imageUrl -> new Image(convertUrlToName(imageUrl)))
                .toList();
    }

    public void update(final Long tripId, final Long itemId, final ItemUpdateRequest itemUpdateRequest) {
        final DayLog dayLog = dayLogRepository.findById(itemUpdateRequest.getDayLogId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        validateAssociationTripAndDayLog(tripId, dayLog);

        final Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ITEM_ID));

        Place updatedPlace = item.getPlace();
        if (itemUpdateRequest.getIsPlaceUpdated() || isChangedToSpot(itemUpdateRequest, item)) {
            updatedPlace = makePlace(itemUpdateRequest.getPlace());
        }

        if (item.getItemType() == ItemType.SPOT && !itemUpdateRequest.getItemType()) {
            updatedPlace = null;
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
                makeExpense(itemUpdateRequest.getExpense()),
                makeUpdatedImages(itemUpdateRequest, item.getImages())
        );

        itemRepository.save(updatedItem);
    }

    private boolean isChangedToSpot(final ItemUpdateRequest itemUpdateRequest, final Item item) {
        return item.getItemType().equals(ItemType.NON_SPOT) && itemUpdateRequest.getPlace() != null;
    }

    private Place makePlace(final PlaceRequest placeRequest) {
        if (placeRequest == null) {
            return null;
        }
        return createPlaceByPlaceRequest(placeRequest);
    }

    private Place createPlaceByPlaceRequest(final PlaceRequest placeRequest) {
        return new Place(
                placeRequest.getName(),
                placeRequest.getLatitude(),
                placeRequest.getLongitude(),
                findCategoryByApiCategory(placeRequest.getApiCategory())
        );
    }

    private Category findCategoryByApiCategory(final List<String> apiCategory) {
        final List<Category> categories = categoryRepository.findByEngNameIn(apiCategory);
        if (categories.isEmpty()) {
            return categoryRepository.findCategoryETC();
        }
        return categories.get(0);
    }

    private List<Image> makeUpdatedImages(final ItemUpdateRequest itemUpdateRequest, final List<Image> originalImages) {
        final List<Image> updatedImages = itemUpdateRequest.getImageUrls().stream()
                .map(imageUrl -> makeUpdatedImage(imageUrl, originalImages))
                .toList();

        return imageRepository.saveAll(updatedImages);
    }

    private Image makeUpdatedImage(final String imageUrl, final List<Image> originalImages) {
        final String imageName = convertUrlToName(imageUrl);

        return originalImages.stream()
                .filter(originalImage -> originalImage.getName().equals(imageName))
                .findAny()
                .orElseGet(() -> new Image(imageName));
    }

    private Expense makeExpense(final ExpenseRequest expenseRequest) {
        if (expenseRequest == null) {
            return null;
        }
        return createExpenseByExpenseRequest(expenseRequest);
    }

    private Expense createExpenseByExpenseRequest(final ExpenseRequest expenseRequest) {
        final Category expenseCategory = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CATEGORY_ID));
        final String currency = CurrencyType.getMappedCurrencyType(expenseRequest.getCurrency()).getCode();

        return new Expense(
                currency,
                new Amount(expenseRequest.getAmount()),
                expenseCategory
        );
    }

    private int getNewItemOrdinal(final DayLog dayLog) {
        return dayLog.getItems().size() + 1;
    }

    public void delete(final Long itemId) {
        final Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ITEM_ID));
        itemRepository.delete(item);
    }

    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .toList();
    }
}
