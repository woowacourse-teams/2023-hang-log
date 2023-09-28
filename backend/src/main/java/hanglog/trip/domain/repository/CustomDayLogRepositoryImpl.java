package hanglog.trip.domain.repository;

import hanglog.trip.domain.DayLog;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomDayLogRepositoryImpl implements CustomDayLogRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveAll(final List<DayLog> dayLogs) {
        final String sql = """
                    INSERT INTO day_log (created_at, modified_at, ordinal, status, title, trip_id) 
                    VALUES (:createdAt, :modifiedAt, :ordinal, :status, :title, :tripId);
                """;
        namedParameterJdbcTemplate.batchUpdate(sql, getDayLogToSqlParameterSources(dayLogs));
    }

    private MapSqlParameterSource[] getDayLogToSqlParameterSources(final List<DayLog> dayLogs)  {
        return dayLogs.stream()
                .map(this::getDayLogToSqlParameterSource)
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource getDayLogToSqlParameterSource(final DayLog dayLog) {
        LocalDateTime now = LocalDateTime.now();
        return new MapSqlParameterSource()
                .addValue("createdAt", now)
                .addValue("modifiedAt", now)
                .addValue("ordinal", dayLog.getOrdinal())
                .addValue("status", dayLog.getStatus().name())
                .addValue("title", dayLog.getTitle())
                .addValue("tripId", dayLog.getTrip().getId());
    }
}
