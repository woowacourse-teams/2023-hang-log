package hanglog.trip.domain.repository;

import hanglog.city.domain.City;
import java.util.List;

public interface CustomTripCityRepository {

    void saveAll(final List<City> cities, final Long tripId);
}
