package hanglog.trip.dto.response;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;
import static hanglog.trip.dto.response.type.TripType.PERSONAL;
import static hanglog.trip.dto.response.type.TripType.PUBLISHED;
import static hanglog.trip.dto.response.type.TripType.SHARED;
import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import hanglog.city.dto.response.CityWithPositionResponse;
import hanglog.member.dto.response.WriterResponse;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.dto.response.type.TripType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class TripDetailResponse {

    private final TripType tripType;
    private final Long id;
    private final List<CityWithPositionResponse> cities;
    private final WriterResponse writer;
    private final Boolean isWriter;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageUrl;
    private final Boolean isLike;
    private final Long likeCount;
    private final LocalDateTime publishedDate;
    private final String sharedCode;
    private final List<DayLogResponse> dayLogs;
    private final Boolean isPublished;

    public static TripDetailResponse personalTrip(final Trip trip, final List<City> cities) {
        return new TripDetailResponse(
                PERSONAL,
                trip.getId(),
                getCityWithPositionResponses(cities),
                null,
                null,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                null,
                null,
                null,
                trip.getSharedCode().orElse(null),
                getDayLogResponses(trip.getDayLogs()),
                trip.isPublished()
        );
    }

    public static TripDetailResponse sharedTrip(final Trip trip, final List<City> cities) {
        return new TripDetailResponse(
                SHARED,
                trip.getId(),
                getCityWithPositionResponses(cities),
                WriterResponse.of(trip.getMember()),
                null,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                null,
                null,
                null,
                trip.getSharedCode().orElse(null),
                getDayLogResponses(trip.getDayLogs()),
                null
        );
    }

    public static TripDetailResponse publishedTrip(
            final Trip trip,
            final List<City> cities,
            final Boolean isWriter,
            final Boolean isLike,
            final Long likeCount,
            final LocalDateTime publishedDate
    ) {
        return new TripDetailResponse(
                PUBLISHED,
                trip.getId(),
                getCityWithPositionResponses(cities),
                WriterResponse.of(trip.getMember()),
                isWriter,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                isLike,
                likeCount,
                publishedDate,
                null,
                getDayLogResponses(trip.getDayLogs()),
                null
        );
    }

    private static List<DayLogResponse> getDayLogResponses(final List<DayLog> dayLogs) {
        return dayLogs.stream()
                .map(DayLogResponse::of)
                .toList();
    }

    private static List<CityWithPositionResponse> getCityWithPositionResponses(final List<City> cities) {
        return cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();
    }
}
