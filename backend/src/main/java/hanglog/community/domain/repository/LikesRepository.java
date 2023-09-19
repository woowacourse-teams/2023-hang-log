package hanglog.community.domain.repository;

import hanglog.community.domain.Likes;
import hanglog.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberIdAndTrip(final Long memberID, final Trip trip);

    Long countLikesByTrip(final Trip trip);
}
