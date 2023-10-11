package hanglog.trip.domain.repository;

import hanglog.community.domain.TripCityInfoDto;
import hanglog.trip.domain.TripCity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripCityRepository extends JpaRepository<TripCity, Long> {

    List<TripCity> findByTripId(Long tripId);

    void deleteAllByTripId(Long tripId);

    @Query("""
            SELECT new hanglog.community.domain.TripCityInfoDto
            (tc.trip.id, tc.city) FROM TripCity tc WHERE tc.trip.id IN :tripIds
            """)
    List<TripCityInfoDto> findTripIdAndCitiesByTripIds(@Param("tripIds") final List<Long> tripIds);
}
