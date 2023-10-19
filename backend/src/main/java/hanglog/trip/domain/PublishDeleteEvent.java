package hanglog.trip.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PublishDeleteEvent {

    private final Long tripId;
}
