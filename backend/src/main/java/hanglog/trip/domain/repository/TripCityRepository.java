package hanglog.trip.domain.repository;

import hanglog.trip.domain.TripCity;
import hanglog.trip.dto.TripCityElement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripCityRepository extends JpaRepository<TripCity, Long> {

    @Query("""
            SELECT new hanglog.trip.dto.TripCityElement (tc.trip.id, tc.city) 
            FROM TripCity tc 
            WHERE tc.trip.id IN :tripIds
            """)
    List<TripCityElement> findTripIdAndCitiesByTripIds(@Param("tripIds") final List<Long> tripIds);

    @Modifying
    @Query("""
            UPDATE TripCity tripCity
            SET tripCity.status = 'DELETED'
            WHERE tripCity.trip.id = :tripId
            """)
    void deleteAllByTripId(@Param("tripId") final Long tripId);

    @Modifying
    @Query("""
            UPDATE TripCity tripCity
            SET tripCity.status = 'DELETED'
            WHERE tripCity.trip.id IN :tripIds
            """)
    void deleteAllByTripIds(@Param("tripId") final List<Long> tripIds);
}
