package hanglog.trip.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.trip.domain.City;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CityWithPositionResponse {

    private static final String CITY_SEPARATOR = ", ";

    private final Long id;
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public static CityWithPositionResponse of(final City city) {
        final String country = city.getCountry();
        final String name = city.getName();
        return new CityWithPositionResponse(
                city.getId(),
                name + CITY_SEPARATOR + country,
                city.getLatitude(),
                city.getLongitude()
        );
    }
}
