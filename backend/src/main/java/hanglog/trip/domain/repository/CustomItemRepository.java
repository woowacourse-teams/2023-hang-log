package hanglog.trip.domain.repository;

import java.util.List;

public interface CustomItemRepository {

    List<Long> findItemIdsByDayLogIds(final List<Long> dayLogIds);
}
