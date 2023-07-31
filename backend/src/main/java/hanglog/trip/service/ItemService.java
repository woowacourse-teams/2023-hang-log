package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CATEGORY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_DAY_LOG_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ITEM_ID;
import static hanglog.image.util.ImageNameUrlConverter.convertUrlToName;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.expense.Expense;
import hanglog.global.exception.BadRequestException;
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
                makePlace(itemRequest.getPlace()),
                dayLog,
                makeExpense(itemRequest.getExpense()),
                makeImages(itemRequest)
        );
        return itemRepository.save(item).getId();
    }

    private List<Image> makeImages(final ItemRequest itemRequest) {
        final List<Image> images = itemRequest.getImageUrls().stream()
                .map(imageUrl -> new Image(convertUrlToName(imageUrl)))
                .toList();

        return imageRepository.saveAll(images);
    }

    public void update(final Long tripId, final Long itemId, final ItemUpdateRequest itemUpdateRequest) {
        final DayLog dayLog = dayLogRepository.findById(itemUpdateRequest.getDayLogId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DAY_LOG_ID));
        final Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ITEM_ID));

        Place updatedPlace = item.getPlace();
        if (itemUpdateRequest.getIsPlaceUpdated()) {
            updatedPlace = makePlace(itemUpdateRequest.getPlace());
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
                getUpdatedImages(itemUpdateRequest, item.getImages())
        );

        itemRepository.save(updatedItem);
    }

    private Place makePlace(final PlaceRequest placeRequest) {
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

    private List<Image> getUpdatedImages(final ItemUpdateRequest itemUpdateRequest, final List<Image> originalImages) {
        final List<Image> updatedImages = itemUpdateRequest.getImageUrls().stream()
                .map(imageUrl -> getUpdatedImage(imageUrl, originalImages))
                .toList();

        return imageRepository.saveAll(updatedImages);
    }

    private Image getUpdatedImage(final String imageUrl, final List<Image> originalImages) {
        final String imageName = convertUrlToName(imageUrl);

        return originalImages.stream()
                .filter(originalImage -> originalImage.getImageName().equals(imageName))
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
        itemRepository.delete(item);
    }

    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .toList();
    }
}
