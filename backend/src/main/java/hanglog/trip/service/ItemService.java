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
import hanglog.expense.domain.repository.ExpenseRepository;
import hanglog.global.exception.BadRequestException;
import hanglog.image.domain.Image;
import hanglog.image.domain.repository.CustomImageRepository;
import hanglog.image.domain.repository.ImageRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.repository.PlaceRepository;
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
    private final PlaceRepository placeRepository;
    private final ExpenseRepository expenseRepository;

    public Long save(final Long tripId, final ItemRequest itemRequest) {
        // TODO: 유저 인가 로직 필요
        final DayLog dayLog = dayLogRepository.findWithItemsById(itemRequest.getDayLogId())
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
        final DayLog dayLog = dayLogRepository.findWithItemDetailsById(itemUpdateRequest.getDayLogId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        validateAssociationTripAndDayLog(tripId, dayLog);

        final Item item = dayLog.getItems().stream()
                .filter(target -> target.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ITEM_ID));

        Place updatedPlace = makeUpdatedPlace(itemUpdateRequest, item);
        if (item.getItemType() == ItemType.SPOT && !itemUpdateRequest.getItemType()) {
            updatedPlace = null;
            placeRepository.delete(item.getPlace());
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
                makeUpdatedExpense(itemUpdateRequest.getExpense(), item.getExpense()),
                makeUpdatedImages(itemUpdateRequest, item)
        );
        itemRepository.save(updatedItem);
    }

    private Place makeUpdatedPlace(final ItemUpdateRequest itemUpdateRequest, final Item item) {
        final Place originalPlace = item.getPlace();
        if (!itemUpdateRequest.getIsPlaceUpdated()) {
            return originalPlace;
        }
        if (originalPlace != null) {
            placeRepository.delete(originalPlace);
        }
        return makePlace(itemUpdateRequest.getPlace());
    }

    private Place makePlace(final PlaceRequest placeRequest) {
        if (placeRequest == null) {
            return null;
        }
        return createPlaceByPlaceRequest(placeRequest);
    }

    private Place createPlaceByPlaceRequest(final PlaceRequest placeRequest) {
        final Place place = new Place(
                placeRequest.getName(),
                placeRequest.getLatitude(),
                placeRequest.getLongitude(),
                findCategoryByApiCategory(placeRequest.getApiCategory())
        );
        return placeRepository.save(place);
    }

    private Category findCategoryByApiCategory(final List<String> apiCategory) {
        final List<Category> categories = categoryRepository.findByEngNameIn(apiCategory);
        if (categories.isEmpty()) {
            return categoryRepository.findCategoryETC();
        }
        return categories.get(0);
    }

    private List<Image> makeUpdatedImages(final ItemUpdateRequest itemUpdateRequest, final Item item) {
        final List<Image> originalImages = item.getImages();
        final List<Image> updatedImages = itemUpdateRequest.getImageUrls().stream()
                .map(imageUrl -> makeUpdatedImage(imageUrl, originalImages, item))
                .toList();

        final List<Image> deletedImages = originalImages.stream()
                .filter(image -> !updatedImages.contains(image))
                .toList();
        imageRepository.deleteAll(deletedImages);

        final List<Image> newImages = updatedImages.stream()
                .filter(image -> !originalImages.contains(image))
                .toList();
        customImageRepository.saveAll(newImages);

        return updatedImages;
    }

    private Image makeUpdatedImage(final String imageUrl, final List<Image> originalImages, final Item item) {
        final String imageName = convertUrlToName(imageUrl);

        return originalImages.stream()
                .filter(originalImage -> originalImage.getName().equals(imageName))
                .findAny()
                .orElseGet(() -> new Image(imageName, item));
    }

    private Expense makeUpdatedExpense(final ExpenseRequest expenseRequest, final Expense originalExpense) {
        final Expense updatedExpense = makeExpense(expenseRequest);
        deleteOriginalExpense(originalExpense, updatedExpense);
        return saveUpdatedExpense(originalExpense, updatedExpense);
    }

    private Expense saveUpdatedExpense(final Expense originalExpense, final Expense updatedExpense) {
        if (updatedExpense == null) {
            return null;
        }
        if (updatedExpense.isDifferent(originalExpense)) {
            return expenseRepository.save(updatedExpense);
        }
        return originalExpense;
    }

    private void deleteOriginalExpense(final Expense originalExpense, final Expense updatedExpense) {
        if (originalExpense == null) {
            return;
        }
        if (originalExpense.isDifferent(updatedExpense)) {
            expenseRepository.delete(originalExpense);
        }
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
        final Expense expense = new Expense(
                currency,
                new Amount(expenseRequest.getAmount()),
                expenseCategory
        );
        return expenseRepository.save(expense);
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
