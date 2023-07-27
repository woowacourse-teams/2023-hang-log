package hanglog.trip.dto.response;

import hanglog.category.dto.CategoryResponse;
import hanglog.trip.domain.Place;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceResponse {

    private final Long id;
    private final String name;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final CategoryResponse category;

    public static PlaceResponse of(final Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getName(),
                place.getLatitude(),
                place.getLongitude(),
                CategoryResponse.of(place.getCategory())
        );
    }
}
