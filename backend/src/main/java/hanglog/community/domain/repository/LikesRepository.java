package hanglog.community.domain.repository;

import hanglog.community.domain.Likes;
import hanglog.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberIdAndTripId(final Long memberId, final Long tripId);

    Long countLikesByTrip(final Trip trip);
}
