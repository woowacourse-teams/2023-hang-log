package hanglog.trip.domain.daylog.repository;

import hanglog.trip.domain.daylog.DayLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayLogRepository extends JpaRepository<DayLog, Long> {
}
