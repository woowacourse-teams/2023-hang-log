package hanglog.trip.domain.repository;

import hanglog.trip.domain.TripOutBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripOutBoxRepository extends JpaRepository<TripOutBox, Long> {
}
