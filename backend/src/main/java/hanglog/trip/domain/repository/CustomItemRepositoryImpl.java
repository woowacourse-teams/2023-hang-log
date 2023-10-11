package hanglog.trip.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
