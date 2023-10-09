package hanglog.trip.domain.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Long> findItemIdsByDayLogIds(final List<Long> dayLogIds) {
        final String sql = """
                SELECT i.id
                FROM item i
                WHERE i.day_log_id IN (:dayLogIds)
                """;
        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("dayLogIds", dayLogIds);
        return namedParameterJdbcTemplate.queryForList(sql, parameters, Long.class);
    }
}
