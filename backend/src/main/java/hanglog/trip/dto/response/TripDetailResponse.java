package hanglog.trip.dto.response;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;
import static lombok.AccessLevel.PRIVATE;

import hanglog.share.domain.SharedTrip;
import hanglog.trip.domain.City;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    private final List<DayLogGetResponse> dayLogs;

    public TripDetailResponse(final Long id, final List<CityWithPositionResponse> cities, final String title,
                              final LocalDate startDate,
                              final LocalDate endDate, final String description, final String imageUrl,
                              final List<DayLogGetResponse> dayLogs) {
        this.id = id;
        this.cities = cities;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sharedCode = null;
        this.dayLogs = dayLogs;
    }

    public static TripDetailResponse of(final Trip trip, final List<City> cities) {
        final List<DayLogGetResponse> dayLogGetResponses = trip.getDayLogs().stream()
                .map(DayLogGetResponse::of)
                .toList();

        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        final Optional<SharedTrip> sharedTrip = Optional.ofNullable(trip.getSharedTrip());

        return sharedTrip.map(value -> new TripDetailResponse(
                trip.getId(),
                cityWithPositionResponses,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                value.getShareCode(),
                dayLogGetResponses
        )).orElseGet(() -> new TripDetailResponse(
                trip.getId(),
                cityWithPositionResponses,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                dayLogGetResponses
        ));
    }
}
