package hanglog.trip.domain.repository;

import hanglog.trip.domain.PublishedTrip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublishedTripRepository extends JpaRepository<PublishedTrip, Long> {
    Optional<PublishedTrip> findByTripId(Long tripId);

    @Query(value = "SELECT * FROM published_trip p WHERE p.status = 'DELETED' AND p.trip_id = :tripId", nativeQuery = true)
    Optional<PublishedTrip> findDeletedByTripId(@Param("tripId") Long tripId);
}
