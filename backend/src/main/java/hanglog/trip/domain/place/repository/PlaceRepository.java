package hanglog.trip.domain.place.repository;

import hanglog.trip.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
