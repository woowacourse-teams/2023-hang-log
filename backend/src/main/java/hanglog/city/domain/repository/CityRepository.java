package hanglog.city.domain.repository;

import hanglog.city.domain.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("""
            SELECT c
            FROM City c
            WHERE c.id in :ids
             """)
    List<City> findCitiesByIds(@Param("ids") final List<Long> ids);
}
