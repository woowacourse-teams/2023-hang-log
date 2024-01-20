package hanglog.city.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CityDetailResponse {

    private final Long id;
    private final String name;
    private final String country;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public static CityDetailResponse of(final City city) {
        return new CityDetailResponse(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getLatitude(),
                city.getLongitude()
        );
    }
}
