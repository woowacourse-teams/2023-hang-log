package hanglog.trip.domain.repository;

import hanglog.trip.domain.Trip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long> {

    boolean existsByMemberIdAndId(final Long memberId, final Long id);

    @Query("SELECT trip FROM Trip trip LEFT JOIN FETCH trip.sharedTrip")
    Optional<Trip> findTripById(final Long id);

    @Query("SELECT trip FROM Trip trip LEFT JOIN FETCH trip.sharedTrip WHERE trip.member.id = :memberId")
    List<Trip> findAllByMemberId(@Param("memberId") final Long memberId);
}
