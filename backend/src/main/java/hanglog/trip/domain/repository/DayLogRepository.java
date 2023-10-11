package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DayLogRepository extends JpaRepository<DayLog, Long> {

    @Modifying
    @Query("""
                UPDATE DayLog dayLog
                SET dayLog.status = 'DELETED'
                WHERE dayLog.id IN :dayLogIds
            """)
    void deleteByIds(@Param("dayLogIds") final List<Long> dayLogIds);
}
