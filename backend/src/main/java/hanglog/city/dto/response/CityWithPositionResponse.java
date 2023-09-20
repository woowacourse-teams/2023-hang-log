package hanglog.city.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CityWithPositionResponse {

    private final Long id;
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public static CityWithPositionResponse of(final City city) {
        return new CityWithPositionResponse(
                city.getId(),
                city.getName(),
                city.getLatitude(),
                city.getLongitude()
        );
    }
}
