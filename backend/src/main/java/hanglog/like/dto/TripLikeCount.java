package hanglog.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TripLikeCount {

    private final long tripId;
    private final long count;
}
