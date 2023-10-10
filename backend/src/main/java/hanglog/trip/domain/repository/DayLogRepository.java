package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DayLogRepository extends JpaRepository<DayLog, Long> {

    @Query("""
            SELECT dayLog
            FROM DayLog dayLog
            LEFT JOIN FETCH dayLog.items items
            WHERE dayLog.id = :dayLogId
            """)
    Optional<DayLog> findWithItemById(@Param("dayLogId") final Long dayLogId);
}
