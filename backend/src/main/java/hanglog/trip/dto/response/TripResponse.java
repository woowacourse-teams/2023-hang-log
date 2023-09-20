package hanglog.trip.dto.response;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;
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
    private final Integer likeCount;

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
                convertNameToUrl(trip.getImageName()),
                // todo: 추후 like 구현 이후 like 개수 입력
                1
        );
    }
}
