package hanglog.like.infrastrcutrue;

import hanglog.like.domain.Likes;
import hanglog.like.domain.repository.CustomLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomLikeRepositoryImpl implements CustomLikeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveAll(final List<Likes> likes) {
        final String sql = """
                    INSERT INTO likes (trip_id, member_id) 
                    VALUES (:tripId, :memberId)
                """;
        namedParameterJdbcTemplate.batchUpdate(sql, getLikesToSqlParameterSources(likes));
    }

    private MapSqlParameterSource[] getLikesToSqlParameterSources(final List<Likes> likes) {
        return likes.stream()
                .map(this::getLikeToSqlParameterSource)
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource getLikeToSqlParameterSource(final Likes likes) {
        return new MapSqlParameterSource()
                .addValue("tripId", likes.getTripId())
                .addValue("memberId", likes.getMemberId());
    }
}
