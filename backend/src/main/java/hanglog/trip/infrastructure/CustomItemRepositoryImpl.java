package hanglog.trip.infrastructure;

import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.dto.ItemElement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

    private static final RowMapper<ItemElement> elementRowMapper = (rs, rowNum) -> new ItemElement(
            rs.getLong(1),
            rs.getLong(2),
            rs.getLong(3)
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ItemElement> findItemIdsByDayLogIds(final List<Long> dayLogIds) {
        final String sql = """
                SELECT i.id, i.expense_id, i.place_id
                FROM item i
                WHERE i.day_log_id IN (:dayLogIds)
                """;
        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("dayLogIds", dayLogIds);
        return namedParameterJdbcTemplate.query(sql, parameters, elementRowMapper);
    }
}
