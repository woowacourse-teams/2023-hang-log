package hanglog.trip.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TripEvent {

    private final Trip trip;
}
