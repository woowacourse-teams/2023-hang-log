package hanglog.trip.domain.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomTripRepositoryImpl implements CustomTripRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> findTripIdsByMemberId(final Long memberId) {
        final String sql = """
                    SELECT t.id
                    FROM trip t
                    WHERE t.member_id = ?
                """;
        return jdbcTemplate.queryForList(sql, Long.class, memberId);
    }
}
