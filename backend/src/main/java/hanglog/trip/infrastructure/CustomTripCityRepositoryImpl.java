package hanglog.trip.infrastructure;

import static hanglog.global.type.StatusType.USABLE;

import hanglog.city.domain.City;
import hanglog.trip.domain.repository.CustomTripCityRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomTripCityRepositoryImpl implements CustomTripCityRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveAll(final List<City> cities, final Long tripId) {
        final String sql = """
                    INSERT INTO trip_city (city_id, created_at, modified_at, status, trip_id) 
                    VALUES (:cityId, :createdAt, :modifiedAt, :status, :tripId)
                """;
        namedParameterJdbcTemplate.batchUpdate(sql, getTripCityToSqlParameterSources(cities, tripId));
    }

    private MapSqlParameterSource[] getTripCityToSqlParameterSources(final List<City> cities, final Long tripId) {
        return cities.stream()
                .map(city -> getTripCityToSqlParameterSource(city, tripId))
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource getTripCityToSqlParameterSource(final City city, final Long tripId) {
        final LocalDateTime now = LocalDateTime.now();
        return new MapSqlParameterSource()
                .addValue("cityId", city.getId())
                .addValue("createdAt", now)
                .addValue("modifiedAt", now)
                .addValue("status", USABLE.name())
                .addValue("tripId", tripId);
    }
}
