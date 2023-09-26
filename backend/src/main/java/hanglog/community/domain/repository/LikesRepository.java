package hanglog.community.domain.repository;

import hanglog.community.domain.Likes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberIdAndTripId(final Long memberId, final Long tripId);

    void deleteByMemberIdAndTripId(final Long memberId, final Long tripId);

    Long countLikesByTripId(final Long tripId);

    @Query("SELECT l.tripId, COUNT(l) AS row_count, " +
            "CASE WHEN MAX(CASE WHEN l.memberId = :memberId THEN 1 ELSE 0 END) = 1 THEN TRUE ELSE FALSE END AS" +
            " member_likes_trip " +
            "FROM Likes l " +
            "WHERE l.tripId IN :tripIds " +
            "GROUP BY l.tripId")
    List<Object[]> countByMemberIdAndTripIds(@Param("memberId") Long memberId, @Param("tripIds") List<Long> tripIds);
}
