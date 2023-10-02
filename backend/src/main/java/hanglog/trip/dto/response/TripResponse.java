package hanglog.trip.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import hanglog.city.dto.response.CityResponse;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class TripResponse {

    private final Long id;
    private final List<CityResponse> cities;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageUrl;

    public static TripResponse of(final Trip trip, final List<City> cities) {
        final List<CityResponse> cityResponses = cities.stream()
                .map(CityResponse::of)
                .toList();

        return new TripResponse(
                trip.getId(),
                cityResponses,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                trip.getImageName()
        );
    }
}
