package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import java.util.List;

public interface CustomDayLogRepository {

    void saveAll(final List<DayLog> dayLogs);
}
