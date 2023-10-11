package hanglog.trip.domain.repository;

import hanglog.trip.domain.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Modifying
    @Query("""
                UPDATE Place place
                SET place.status = 'DELETED'
                WHERE place.id IN :placeIds
            """)
    void deleteByIds(@Param("placeIds") final List<Long> placeIds);
}
