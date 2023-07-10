package hanglog.trip.domain.repository;

import hanglog.trip.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
