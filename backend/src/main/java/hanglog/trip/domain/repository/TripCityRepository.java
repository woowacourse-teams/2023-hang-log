package hanglog.trip.domain.repository;

import hanglog.trip.domain.TripCity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripCityRepository extends JpaRepository<TripCity, Long> {

    List<TripCity> findByTripId(Long tripId);

    @Modifying
    @Query("""
            UPDATE TripCity tripCity
            SET tripCity.status = 'DELETED'
            WHERE tripCity.trip.id = :tripId
            """)
    void deleteAllByTripId(@Param("tripId") final Long tripId);
}
