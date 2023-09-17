package hanglog.trip.domain.repository;

import hanglog.trip.domain.Like;
import hanglog.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByMemberIdAndTrip(final Long memberID, final Trip trip);

    Long countLikeByTrip(final Trip trip);
}
