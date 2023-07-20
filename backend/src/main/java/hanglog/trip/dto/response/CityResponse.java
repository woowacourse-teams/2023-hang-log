package hanglog.trip.dto.response;

import hanglog.trip.domain.City;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CityResponse {

    private final Long id;
    private final String name;

    public static CityResponse of(final City city) {
        String country = city.getCountry();
        String name = city.getName();
        return new CityResponse(city.getId(), country + ", " + name);
    }
}
