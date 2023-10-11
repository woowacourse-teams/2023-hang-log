package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import java.util.List;

public interface CustomDayLogRepository {

    void saveAll(final List<DayLog> dayLogs);

    List<Long> findDayLogIdsByTripId(final Long tripId);

    List<Long> findDayLogIdsByTripIds(final List<Long> tripIds);
}
