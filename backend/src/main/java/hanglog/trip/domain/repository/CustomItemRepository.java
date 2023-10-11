package hanglog.trip.domain.repository;

import java.util.List;

public interface CustomItemRepository {

    void updateOrdinals(final List<Long> orderedItemIds);
}
