package hanglog.trip.infrastructure;

import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.dto.ItemElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

    @Override
    public void updateOrdinals(final List<Long> orderedItemIds) {
        final String sql = "UPDATE item SET ordinal = :newOrdinal WHERE id = :itemId";
        final SqlParameterSource[] sqlParameterSources = getUpdateOrdinalsSqlParameterSources(orderedItemIds);
        namedParameterJdbcTemplate.batchUpdate(sql, sqlParameterSources);
    }

    private SqlParameterSource[] getUpdateOrdinalsSqlParameterSources(final List<Long> orderedItemIds) {
        final SqlParameterSource[] sqlParameterSources = new MapSqlParameterSource[orderedItemIds.size()];
        for (int i = 0; i < orderedItemIds.size(); i++) {
            final Long itemId = orderedItemIds.get(i);
            final int newOrdinal = i + 1;
            final Map<String, Object> sqlParameterSource = new HashMap<>();
            sqlParameterSource.put("newOrdinal", newOrdinal);
            sqlParameterSource.put("itemId", itemId);
            sqlParameterSources[i] = new MapSqlParameterSource(sqlParameterSource);
        }
        return sqlParameterSources;
    }
}
