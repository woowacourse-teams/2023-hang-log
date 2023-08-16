package hanglog.trip.domain.repository;

import hanglog.trip.domain.Trip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripRepository extends JpaRepository<Trip, Long> {

    boolean existsByMemberIdAndId(final Long memberId, final Long id);

    @Query("SELECT trip FROM Trip trip JOIN FETCH trip.sharedTrip")
    Optional<Trip> findTripById(final Long id);

    List<Trip> findAllByMemberId(final Long memberId);
}
