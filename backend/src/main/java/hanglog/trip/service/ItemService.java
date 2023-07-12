package hanglog.trip.service;

import hanglog.category.Category;
import hanglog.category.repository.CategoryRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.Place;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.type.ItemType;
import hanglog.trip.presentation.dto.request.ItemRequest;
import hanglog.trip.presentation.dto.request.PlaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final DayLogRepository dayLogRepository;

    public Long save(final Long tripId, final ItemRequest itemRequest) {
        // TODO: 유저 인가 로직 필요
        PlaceRequest placeRequest = itemRequest.getPlace();
        Category category = categoryRepository.findById(placeRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 카테고리가 존재하지 않습니다."));

        Place place = new Place(placeRequest.getName(),
                placeRequest.getAddress(),
                placeRequest.getLatitude(),
                placeRequest.getLongitude(),
                category);

        DayLog dayLog = dayLogRepository.findById(itemRequest.getDayLogId())
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));

        Item item = new Item(ItemType.getItemTypeByIsSpot(itemRequest.getItemType()),
                itemRequest.getTitle(),
                itemRequest.getDayLogOrdinal(),
                itemRequest.getRating(),
                itemRequest.getMemo(),
                place,
                dayLog
        );
        return itemRepository.save(item).getId();
    }
}
