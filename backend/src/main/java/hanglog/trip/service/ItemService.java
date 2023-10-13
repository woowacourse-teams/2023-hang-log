package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_ASSOCIATE_DAYLOG_WITH_TRIP;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CATEGORY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_DAY_LOG_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ITEM_ID;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.currency.domain.type.CurrencyType;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;
import hanglog.expense.domain.repository.ExpenseRepository;
import hanglog.global.exception.BadRequestException;
import hanglog.image.domain.Image;
import hanglog.image.domain.S3ImageEvent;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final DayLogRepository dayLogRepository;
    private final PlaceRepository placeRepository;
    private final ExpenseRepository expenseRepository;
    private final ImageRepository imageRepository;
    private final CustomImageRepository customImageRepository;
    private final ApplicationEventPublisher publisher;

    public Long save(final Long tripId, final ItemRequest itemRequest) {
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
        return itemRequest.getImageNames().stream()
                .map(Image::new)
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
        final Item updatedItem = new Item(
                itemId,
                ItemType.getItemTypeByIsSpot(itemUpdateRequest.getItemType()),
                itemUpdateRequest.getTitle(),
                item.getOrdinal(),
                itemUpdateRequest.getRating(),
                itemUpdateRequest.getMemo(),
                makeUpdatedPlace(itemUpdateRequest, item),
                dayLog,
                makeUpdatedExpense(itemUpdateRequest.getExpense(), item.getExpense()),
                makeUpdatedImages(itemUpdateRequest, item)
        );
        itemRepository.save(updatedItem);
    }

    private Place makeUpdatedPlace(final ItemUpdateRequest itemUpdateRequest, final Item item) {
        final Place originalPlace = item.getPlace();
        if (isPlaceNotUpdated(itemUpdateRequest, item)) {
            return originalPlace;
        }
        final Place updatedPlace = createPlaceByPlaceRequest(itemUpdateRequest.getPlace());
        deleteNotUsedPlace(originalPlace);
        return saveNewlyUpdatedPlace(updatedPlace);
    }

    boolean isPlaceNotUpdated(final ItemUpdateRequest itemUpdateRequest, final Item item) {
        if (!item.isSpot() && !itemUpdateRequest.getIsPlaceUpdated()) {
            return true;
        }
        if (item.isSpot() && !itemUpdateRequest.getIsPlaceUpdated() && itemUpdateRequest.getItemType()) {
            return true;
        }
        return false;
    }

    private void deleteNotUsedPlace(final Place place) {
        if (place == null) {
            return;
        }
        placeRepository.delete(place);
    }

    private Place saveNewlyUpdatedPlace(final Place updatedPlace) {
        if (updatedPlace == null) {
            return null;
        }
        return placeRepository.save(updatedPlace);
    }

    private Place makePlace(final PlaceRequest placeRequest) {
        final Place place = createPlaceByPlaceRequest(placeRequest);
        if (place == null) {
            return null;
        }
        return placeRepository.save(place);
    }

    private Place createPlaceByPlaceRequest(final PlaceRequest placeRequest) {
        if (placeRequest == null) {
            return null;
        }
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

    private List<Image> makeUpdatedImages(final ItemUpdateRequest itemUpdateRequest, final Item item) {
        final List<Image> originalImages = item.getImages();
        final List<Image> updatedImages = itemUpdateRequest.getImageNames().stream()
                .map(imageName -> makeUpdatedImage(imageName, originalImages, item))
                .toList();

        deleteNotUsedImages(originalImages, updatedImages);
        saveNewlyUpdatedImages(originalImages, updatedImages);
        return updatedImages;
    }

    private Image makeUpdatedImage(final String imageName, final List<Image> originalImages, final Item item) {
        return originalImages.stream()
                .filter(originalImage -> originalImage.getName().equals(imageName))
                .findAny()
                .orElseGet(() -> new Image(imageName, item));
    }

    private void saveNewlyUpdatedImages(final List<Image> originalImages, final List<Image> updatedImages) {
        final List<Image> newImages = updatedImages.stream()
                .filter(image -> !originalImages.contains(image))
                .toList();
        customImageRepository.saveAll(newImages);
    }

    private void deleteNotUsedImages(final List<Image> originalImages, final List<Image> updatedImages) {
        final List<Image> deletedImages = originalImages.stream()
                .filter(image -> !updatedImages.contains(image))
                .toList();
        if (deletedImages.isEmpty()) {
            return;
        }
        customImageRepository.deleteAll(deletedImages);
        deletedImages.forEach(image -> publisher.publishEvent(new S3ImageEvent(image.getName())));
    }

    private Expense makeUpdatedExpense(final ExpenseRequest expenseRequest, final Expense originalExpense) {
        final Expense updatedExpense = createExpenseByExpenseRequest(expenseRequest);
        if (isExpenseNotUpdated(updatedExpense, originalExpense)) {
            return originalExpense;
        }
        deleteNotUsedExpense(originalExpense);
        return saveNewlyUpdatedExpense(updatedExpense);
    }

    private void deleteNotUsedExpense(final Expense expense) {
        if (expense == null) {
            return;
        }
        expenseRepository.delete(expense);
    }

    private boolean isExpenseNotUpdated(final Expense updatedExpense, final Expense originalExpense) {
        if (updatedExpense == null && originalExpense == null) {
            return true;
        }
        if (updatedExpense != null && updatedExpense.equals(originalExpense)) {
            return true;
        }
        return false;
    }

    private Expense saveNewlyUpdatedExpense(final Expense updatedExpense) {
        if (updatedExpense == null) {
            return null;
        }
        return expenseRepository.save(updatedExpense);
    }

    private Expense makeExpense(final ExpenseRequest expenseRequest) {
        final Expense expense = createExpenseByExpenseRequest(expenseRequest);
        if (expense == null) {
            return null;
        }
        return expenseRepository.save(expense);
    }

    private Expense createExpenseByExpenseRequest(final ExpenseRequest expenseRequest) {
        if (expenseRequest == null) {
            return null;
        }
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

        if (!item.getImages().isEmpty()) {
            imageRepository.deleteByItemId(itemId);
        }
        if (item.getPlace() != null) {
            placeRepository.deleteById(item.getPlace().getId());
        }
        if (item.getExpense() != null) {
            expenseRepository.deleteById(item.getExpense().getId());
        }
        itemRepository.deleteById(itemId);
    }

    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .toList();
    }
}
