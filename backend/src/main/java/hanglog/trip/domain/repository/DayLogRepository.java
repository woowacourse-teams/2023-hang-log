package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DayLogRepository extends JpaRepository<DayLog, Long> {

    @Query("""
            SELECT dayLog
            FROM DayLog dayLog
            LEFT JOIN FETCH dayLog.items items
            WHERE dayLog.id = :dayLogId
            """)
    Optional<DayLog> findWithItemsById(@Param("dayLogId") final Long dayLogId);

    @Query("""
            SELECT dayLog
            FROM DayLog dayLog
            LEFT JOIN FETCH dayLog.items items
            LEFT JOIN FETCH items.images images
            LEFT JOIN FETCH items.expense expense
            LEFT JOIN FETCH items.place place
            LEFT JOIN FETCH expense.category expense_category
            LEFT JOIN FETCH place.category place_category
            WHERE dayLog.id = :dayLogId
            """)
    Optional<DayLog> findWithItemDetailsById(@Param("dayLogId") final Long dayLogId);

    @Modifying
    @Query("""
                UPDATE DayLog dayLog
                SET dayLog.status = 'DELETED'
                WHERE dayLog.id IN :dayLogIds
            """)
    void deleteByIds(@Param("dayLogIds") final List<Long> dayLogIds);
}
