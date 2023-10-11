package hanglog.community.domain;

import hanglog.city.domain.City;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TripCityInfoDto {

    private final Long tripId;
    private final City city;

    public static Map<Long, List<City>> toMap(final List<TripCityInfoDto> tripCityInfoDtos) {
        final Map<Long, List<City>> map = new HashMap<>();
        for (final TripCityInfoDto tripCityInfoDto : tripCityInfoDtos) {
            final Long tripId = tripCityInfoDto.getTripId();
            final City city = tripCityInfoDto.getCity();
            map.computeIfAbsent(tripId, k -> new ArrayList<>()).add(city);
        }
        return map;
    }
}
