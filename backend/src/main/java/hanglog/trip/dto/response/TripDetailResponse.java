package hanglog.trip.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import hanglog.city.dto.response.CityWithPositionResponse;
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
    private final Boolean isPublished;

    public static TripDetailResponse of(final Trip trip, final String imageUrl, final List<City> cities) {
        final List<DayLogResponse> dayLogResponses = trip.getDayLogs().stream()
                .map(DayLogResponse::of)
                .toList();

        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        final String sharedCode = trip.getSharedCode().orElse(null);

        return new TripDetailResponse(
                trip.getId(),
                cityWithPositionResponses,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                imageUrl,
                sharedCode,
                dayLogResponses,
                trip.isPublished()
        );
    }
}
