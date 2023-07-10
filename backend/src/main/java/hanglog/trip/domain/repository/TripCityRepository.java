package hanglog.trip.domain.repository;

import hanglog.trip.domain.TripCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripCityRepository extends JpaRepository<TripCity, Long> {
}
