package hanglog.city.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.trip.domain.City;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CityResponse {

    private static final String CITY_SEPARATOR = ", ";

    private final Long id;
    private final String name;

    public static CityResponse withCountry(final City city) {
        final String country = city.getCountry();
        final String name = city.getName();
        return new CityResponse(city.getId(), name + CITY_SEPARATOR + country);
    }

    public static CityResponse of(final City city) {
        return new CityResponse(city.getId(), city.getName());
    }
}
