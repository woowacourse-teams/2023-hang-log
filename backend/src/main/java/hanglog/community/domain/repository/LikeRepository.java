package hanglog.community.domain.repository;

import hanglog.community.domain.LikeInfoDto;
import hanglog.community.domain.Likes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberIdAndTripId(final Long memberId, final Long tripId);

    void deleteByMemberIdAndTripId(final Long memberId, final Long tripId);

    Long countLikesByTripId(final Long tripId);

    @Query("""
            SELECT new hanglog.community.domain.LikeInfoDto
            (l.tripId, COUNT(l.memberId), EXISTS(SELECT 1 FROM Likes l_1 WHERE l_1.memberId = :memberId AND l_1.tripId = l.tripId))
            FROM Likes l
            WHERE l.tripId in :tripIds
            GROUP BY l.tripId
             """)
    List<LikeInfoDto> countByMemberIdAndTripId(@Param("memberId") final Long memberId, @Param("tripIds") final List<Long> tripIds);
}
