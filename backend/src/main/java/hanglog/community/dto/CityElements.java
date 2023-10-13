package hanglog.community.dto;

import hanglog.city.domain.City;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityElements {

    private final List<CityElement> cityElements;

    public static Map<Long, List<City>> toCityMap(final List<CityElement> cityElements) {
        final Map<Long, List<City>> map = new HashMap<>();
        for (final CityElement cityElement : cityElements) {
            final Long tripId = cityElement.getTripId();
            final City city = cityElement.getCity();
            map.computeIfAbsent(tripId, k -> new ArrayList<>()).add(city);
        }
        return map;
    }
}
