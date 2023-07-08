package hanglog.trip.domain.trip.repository;

import hanglog.trip.domain.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
