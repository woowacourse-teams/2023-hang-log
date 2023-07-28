package hanglog.city.dto.response;

import hanglog.trip.domain.TripCity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CityExpenseResponse {

    private final long id;
    private final String name;

    public static CityExpenseResponse of(final TripCity tripCity) {
        return new CityExpenseResponse(
                tripCity.getCity().getId(),
                tripCity.getCity().getName()
        );
    }
}
