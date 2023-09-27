package hanglog.trip.domain.repository;

import hanglog.community.domain.PublishedTrip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishedTripRepository extends JpaRepository<PublishedTrip, Long> {

    void deleteByTripId(final Long tripId);

    boolean existsByTripId(final Long tripId);

    Optional<PublishedTrip> findByTripId(final Long tripId);
}
