package hanglog.community.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.city.domain.City;
import hanglog.city.dto.response.CityResponse;
import hanglog.share.dto.response.WriterResponse;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class CommunityTripResponse {

    private final Long id;
    private final List<CityResponse> cities;
    private final WriterResponse writer;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String imageUrl;
    private final Boolean isLike;
    private final Long likeCount;

    public static CommunityTripResponse of(
            final Trip trip,
            final String imageUrl,
            final List<City> cities,
            final boolean isLike,
            final Long likeCount
    ) {
        final List<CityResponse> cityResponses = cities.stream()
                .map(CityResponse::of)
                .toList();

        return new CommunityTripResponse(
                trip.getId(),
                cityResponses,
                WriterResponse.of(trip.getMember()),
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                imageUrl,
                isLike,
                likeCount
        );
    }
}
