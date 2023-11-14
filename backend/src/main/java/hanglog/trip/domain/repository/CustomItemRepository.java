package hanglog.trip.domain.repository;

import hanglog.trip.domain.Item;
import hanglog.trip.dto.ItemElement;
import java.util.List;

public interface CustomItemRepository {

    List<ItemElement> findItemIdsByDayLogIds(final List<Long> dayLogIds);

    void updateOrdinals(final List<Long> orderedItemIds);

    void saveAll(List<Item> items);
}
