package hanglog.like.domain.repository;

import hanglog.like.domain.Likes;
import hanglog.like.dto.LikeElement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Query(value = """
            SELECT l.trip_id AS tripId, COUNT(l.id) AS likeCount, GROUP_CONCAT(l.member_id) AS memberIds
            FROM Likes l
            WHERE l.trip_id IN :tripIds
            GROUP BY l.trip_id
            """, nativeQuery = true)
    List<LikeElement> findLikeElementByTripIds(@Param("tripIds") final List<Long> tripIds);

    @Query(value = """
            SELECT l.trip_id AS tripId, COUNT(l.id) AS likeCount, GROUP_CONCAT(l.member_id) AS memberIds
            FROM Likes l
            WHERE l.trip_id = :tripId
            GROUP BY l.trip_id
            """, nativeQuery = true)
    Optional<LikeElement> findLikesElementByTripId(@Param("tripId") final Long tripId);

    @Query("DELETE FROM Likes WHERE tripId IN :tripIds")
    void deleteByTripIds(final Set<Long> tripIds);
}
