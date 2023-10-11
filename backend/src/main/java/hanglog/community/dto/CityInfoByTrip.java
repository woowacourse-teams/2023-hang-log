package hanglog.community.dto;

import hanglog.city.domain.City;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CityInfoByTrip {

    private final Long tripId;
    private final City city;

    public static Map<Long, List<City>> toCityMap(final List<CityInfoByTrip> cityInfoByTripList) {
        final Map<Long, List<City>> map = new HashMap<>();
        for (final CityInfoByTrip cityInfoByTrip : cityInfoByTripList) {
            final Long tripId = cityInfoByTrip.getTripId();
            final City city = cityInfoByTrip.getCity();
            map.computeIfAbsent(tripId, k -> new ArrayList<>()).add(city);
        }
        return map;
    }
}
