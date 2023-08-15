package hanglog.trip.domain.repository;

import hanglog.trip.domain.Trip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT trip FROM Trip trip JOIN FETCH trip.sharedTrip WHERE trip.id IN :id")
    Optional<Trip> findTripByIdWithFetch(final Long id);
}
