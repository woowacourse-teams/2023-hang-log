package hanglog.trip.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PublishedDeleteEvent {

    private final Long tripId;
}
