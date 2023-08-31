package hanglog.trip.dto.response;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;
import static lombok.AccessLevel.PRIVATE;

import hanglog.city.dto.response.CityWithPositionResponse;
import hanglog.city.domain.City;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class TripDetailResponse {

    private final Long id;
    private final List<CityWithPositionResponse> cities;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageUrl;
    private final String sharedCode;
    private final List<DayLogResponse> dayLogs;

    public static TripDetailResponse of(final Trip trip, final List<City> cities) {
        final List<DayLogResponse> dayLogResponses = trip.getDayLogs().stream()
                .map(DayLogResponse::of)
                .toList();

        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        return new TripDetailResponse(
                trip.getId(),
                cityWithPositionResponses,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                trip.getSharedCode(),
                dayLogResponses
        );
    }
}
