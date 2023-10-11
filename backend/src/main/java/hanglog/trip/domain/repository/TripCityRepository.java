package hanglog.trip.domain.repository;

import hanglog.community.dto.CityInfoByTrip;
import hanglog.trip.domain.TripCity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripCityRepository extends JpaRepository<TripCity, Long> {

    List<TripCity> findByTripId(Long tripId);

    void deleteAllByTripId(Long tripId);

    @Query("""
            SELECT new hanglog.community.dto.CityInfoByTrip
            (tc.trip.id, tc.city) FROM TripCity tc WHERE tc.trip.id IN :tripIds
            """)
    List<CityInfoByTrip> findTripIdAndCitiesByTripIds(@Param("tripIds") final List<Long> tripIds);
}
