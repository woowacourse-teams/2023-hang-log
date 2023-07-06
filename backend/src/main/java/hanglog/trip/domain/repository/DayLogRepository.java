package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayLogRepository extends JpaRepository<DayLog, Long> {
}
