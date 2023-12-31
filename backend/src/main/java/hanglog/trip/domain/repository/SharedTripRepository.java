package hanglog.trip.domain.repository;

import hanglog.trip.domain.SharedTrip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharedTripRepository extends JpaRepository<SharedTrip, Long> {

    @Query("""
            SELECT sharedTrip
            FROM SharedTrip sharedTrip
            LEFT JOIN FETCH sharedTrip.trip trip
            WHERE sharedTrip.sharedCode = :sharedCode
            """)
    Optional<SharedTrip> findBySharedCode(final String sharedCode);

    @Modifying
    @Query("""
            UPDATE SharedTrip sharedTrip
            SET sharedTrip.status = 'DELETED'
            WHERE sharedTrip.trip.id = :tripId
            """)
    void deleteByTripId(@Param("tripId") final Long tripId);

    @Modifying
    @Query("""
            UPDATE SharedTrip sharedTrip
            SET sharedTrip.status = 'DELETED'
            WHERE sharedTrip.trip.id IN :tripIds
            """)
    void deleteByTripIds(@Param("tripIds") final List<Long> tripIds);
}
