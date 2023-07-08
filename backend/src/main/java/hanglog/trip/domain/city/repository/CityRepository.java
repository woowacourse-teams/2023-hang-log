package hanglog.trip.domain.city.repository;

import hanglog.trip.domain.city.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
