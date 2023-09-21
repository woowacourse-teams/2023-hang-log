package hanglog.community.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class RecommendTripListResponse {

    private final String title;
    private final List<CommunityTripResponse> trips;
}
