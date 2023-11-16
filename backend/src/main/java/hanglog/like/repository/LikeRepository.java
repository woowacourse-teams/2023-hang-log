package hanglog.like.repository;

import hanglog.like.domain.Likes;
import hanglog.like.dto.LikeElement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberIdAndTripId(final Long memberId, final Long tripId);

    void deleteByMemberIdAndTripId(final Long memberId, final Long tripId);

    @Query("""
            SELECT new hanglog.like.dto.LikeElement
            (l.tripId, COUNT(l.memberId), EXISTS(SELECT 1 FROM Likes l_1 WHERE l_1.memberId = :memberId AND l_1.tripId = l.tripId))
            FROM Likes l
            WHERE l.tripId in :tripIds
            GROUP BY l.tripId
             """)
    List<LikeElement> findLikeCountAndIsLikeByTripIds(@Param("memberId") final Long memberId,
                                                      @Param("tripIds") final List<Long> tripIds);

    @Query("""
            SELECT new hanglog.like.dto.LikeElement
            (l.tripId, COUNT(l.memberId), EXISTS(SELECT 1 FROM Likes l_1 WHERE l_1.memberId = :memberId AND l_1.tripId = l.tripId))
            FROM Likes l
            WHERE l.tripId = :tripId
            GROUP BY l.tripId
             """)
    Optional<LikeElement> findLikeCountAndIsLikeByTripId(@Param("memberId") final Long memberId,
                                                         @Param("tripId") final Long tripId);
}
