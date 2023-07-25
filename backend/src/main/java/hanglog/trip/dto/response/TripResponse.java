package hanglog.trip.dto.response;

import hanglog.trip.domain.City;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TripResponse {

    private final Long id;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageUrl;
    private final List<CityWithPositionResponse> cities;
    private final List<DayLogGetResponse> dayLogs;


    public static TripResponse of(final Trip trip, final List<City> cities) {
        final List<DayLogGetResponse> dayLogGetResponses = trip.getDayLogs().stream()
                .map(DayLogGetResponse::of)
                .toList();

        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        return new TripResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                "http://image.url", // TODO: imageUrl 추가
                cityWithPositionResponses,
                dayLogGetResponses
        );
    }
}
