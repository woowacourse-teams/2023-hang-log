package hanglog.city.domain.repository;

import hanglog.city.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
