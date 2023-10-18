package hanglog.trip.dto;

import hanglog.city.domain.City;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TripCityElements {

    private final List<TripCityElement> elements;

    public Map<Long, List<City>> toCityMap() {
        final Map<Long, List<City>> map = new HashMap<>();
        for (final TripCityElement tripCityElement : elements) {
            final Long tripId = tripCityElement.getTripId();
            final City city = tripCityElement.getCity();
            map.computeIfAbsent(tripId, k -> new ArrayList<>()).add(city);
        }
        return map;
    }
}
