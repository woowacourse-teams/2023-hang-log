package hanglog.trip.domain.repository;

import hanglog.community.domain.PublishedTrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishedTripRepository extends JpaRepository<PublishedTrip, Long> {

    void deleteByTripId(Long tripId);

    boolean existsByTripId(Long tripId);
}
