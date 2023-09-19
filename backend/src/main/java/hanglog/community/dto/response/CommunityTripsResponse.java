package hanglog.community.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityTripsResponse {

    final List<CommunitySingleTripResponse> trips;
    final Long lastPageIndex;
}
