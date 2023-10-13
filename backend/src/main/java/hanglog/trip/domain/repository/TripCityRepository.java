package hanglog.trip.domain.repository;

import hanglog.community.dto.CityElement;
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

    @Query("""
            SELECT new hanglog.community.dto.CityElement (tc.trip.id, tc.city) 
            FROM TripCity tc 
            WHERE tc.trip.id IN :tripIds
            """)
    List<CityElement> findTripIdAndCitiesByTripIds(@Param("tripIds") final List<Long> tripIds);
}
