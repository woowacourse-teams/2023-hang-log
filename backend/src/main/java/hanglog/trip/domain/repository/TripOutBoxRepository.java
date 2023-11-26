package hanglog.trip.domain.repository;

import hanglog.trip.domain.TripOutBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripOutBoxRepository extends JpaRepository<TripOutBox, Long> {

    @Modifying
    @Query("""
            UPDATE TripOutBox  tripOutBox
            SET tripOutBox.status = 'DELETED'
            WHERE tripOutBox.tripId = :tripId
            """)
    void deleteByTripId(@Param("tripId") final Long tripId);
}
