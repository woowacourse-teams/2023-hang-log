package hanglog.trip.dto.response;

import hanglog.trip.domain.City;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CityResponse {

    private static final String CITY_SEPARATOR =", ";

    private final Long id;
    private final String name;

    public static CityResponse of(final City city) {
        final String country = city.getCountry();
        final String name = city.getName();
        return new CityResponse(city.getId(), country + CITY_SEPARATOR + name);
    }
}
