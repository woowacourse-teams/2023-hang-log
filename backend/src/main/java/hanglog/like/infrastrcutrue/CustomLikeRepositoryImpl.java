package hanglog.like.infrastrcutrue;

import hanglog.like.domain.Likes;
import hanglog.like.domain.repository.CustomLikeRepository;
import hanglog.like.dto.LikeElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class CustomLikeRepositoryImpl implements CustomLikeRepository {

    private static final RowMapper<LikeElement> likeElementRowMapper = (rs, rowNum) ->
            new LikeElement(
                    rs.getLong("tripId"),
                    rs.getLong("likeCount"),
                    parseMemberIds(rs.getString("memberIds")));

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

    @Override
    public Optional<LikeElement> findLikesElementByTripId(final Long tripId) {
        final String sql = """
                SELECT l.trip_id AS tripId, COUNT(l.id) AS likeCount, GROUP_CONCAT(l.member_id) AS memberIds
                FROM likes l
                WHERE l.trip_id = :tripId
                GROUP BY l.trip_id
                """;

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("tripId", tripId);
        final List<LikeElement> results = namedParameterJdbcTemplate.query(sql, parameters, likeElementRowMapper);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }

    @Override
    public List<LikeElement> findLikeElementByTripIds(final List<Long> tripIds) {
        final String sql = """
                SELECT l.trip_id AS tripId, COUNT(l.id) AS likeCount, GROUP_CONCAT(l.member_id) AS memberIds
                FROM likes l
                WHERE l.trip_id IN (:tripIds)
                GROUP BY l.trip_id
                """;

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("tripIds", tripIds);
        return namedParameterJdbcTemplate.query(sql, parameters, likeElementRowMapper);
    }

    private static Set<Long> parseMemberIds(final String memberIds) {
        if (!StringUtils.hasText(memberIds)) {
            return Collections.emptySet();
        }
        final String[] idArray = memberIds.split(",");
        return Arrays.stream(idArray)
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }
}
