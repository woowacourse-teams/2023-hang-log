package hanglog.share.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import hanglog.city.dto.response.CityWithPositionResponse;
import hanglog.trip.domain.Trip;
import hanglog.trip.dto.response.DayLogResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class SharedTripDetailResponse {

    private final Long id;
    private final List<CityWithPositionResponse> cities;
    private final WriterResponse writer;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageName;
    private final String sharedCode;
    private final List<DayLogResponse> dayLogs;

    public static SharedTripDetailResponse of(final Trip trip, final List<City> cities) {
        final List<DayLogResponse> dayLogResponses = trip.getDayLogs().stream()
                .map(DayLogResponse::of)
                .toList();

        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        final String sharedCode = trip.getSharedCode().orElse(null);

        return new SharedTripDetailResponse(
                trip.getId(),
                cityWithPositionResponses,
                WriterResponse.of(trip.getMember()),
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                trip.getImageName(),
                sharedCode,
                dayLogResponses
        );
    }
}
