package hanglog.community.domain.repository;

import hanglog.community.domain.PublishedTrip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublishedTripRepository extends JpaRepository<PublishedTrip, Long> {

    boolean existsByTripId(final Long tripId);

    Optional<PublishedTrip> findByTripId(final Long tripId);

    @Modifying
    @Query("""
            UPDATE PublishedTrip publishedTrip
            SET publishedTrip.status = 'DELETED'
            WHERE publishedTrip.tripId = :tripId
            """)
    void deleteByTripId(@Param("tripId") final Long tripId);

    @Modifying
    @Query("""
            UPDATE PublishedTrip publishedTrip
            SET publishedTrip.status = 'DELETED'
            WHERE publishedTrip.tripId IN :tripIds
            """)
    void deleteByTripIds(@Param("tripIds") final List<Long> tripIds);
}
