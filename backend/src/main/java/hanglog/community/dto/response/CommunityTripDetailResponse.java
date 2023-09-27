package hanglog.community.dto.response;

import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;
import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import hanglog.city.dto.response.CityWithPositionResponse;
import hanglog.community.domain.TripInfo;
import hanglog.share.dto.response.WriterResponse;
import hanglog.trip.domain.Trip;
import hanglog.trip.dto.response.DayLogResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CommunityTripDetailResponse {

    private final Long id;
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
    private final List<CityWithPositionResponse> cities;
    private final List<DayLogResponse> dayLogs;

    public static CommunityTripDetailResponse of(
            final Trip trip,
            final List<City> cities,
            final Boolean isWriter,
            final TripInfo tripInfo,
            final LocalDateTime publishedDate
    ) {
        final List<DayLogResponse> dayLogResponses = trip.getDayLogs().stream()
                .map(DayLogResponse::of)
                .toList();

        final List<CityWithPositionResponse> cityWithPositionResponses = cities.stream()
                .map(CityWithPositionResponse::of)
                .toList();

        return new CommunityTripDetailResponse(
                trip.getId(),
                WriterResponse.of(trip.getMember()),
                isWriter,
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                convertNameToUrl(trip.getImageName()),
                tripInfo.getIsLike(),
                tripInfo.getLikeCount(),
                publishedDate,
                cityWithPositionResponses,
                dayLogResponses
        );
    }
}
