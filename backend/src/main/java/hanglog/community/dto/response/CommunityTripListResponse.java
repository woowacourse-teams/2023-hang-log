package hanglog.community.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityTripListResponse {

    final List<CommunityTripResponse> trips;
    final Long lastPageIndex;
}
