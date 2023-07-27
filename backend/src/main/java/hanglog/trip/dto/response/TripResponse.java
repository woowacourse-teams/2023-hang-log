package hanglog.trip.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.trip.domain.City;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class TripResponse {

    private final Long id;
    private final List<CityWithPositionResponse> cities;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageUrl;

    public static TripResponse of(final Trip trip, final List<City> cities) {
        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        return new TripResponse(
                trip.getId(),
                cityWithPositionResponses,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                trip.getImageUrl()
        );
    }
}
