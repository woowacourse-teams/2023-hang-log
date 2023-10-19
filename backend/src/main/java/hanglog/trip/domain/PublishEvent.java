package hanglog.trip.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PublishEvent {

    private final Long tripId;
}
