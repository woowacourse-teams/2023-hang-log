package hanglog.share.domain.repository;

import hanglog.share.domain.SharedTrip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharedTripRepository extends JpaRepository<SharedTrip, Long> {

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
