package hanglog.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class TripDeleteEvent extends Event implements Serializable {

    @JsonProperty
    private Long tripId;

    public TripDeleteEvent(final Long outboxId, final EventType eventType, final Long tripId) {
        super(outboxId, eventType);
        this.tripId = tripId;
    }

    public TripDeleteEvent(final Long tripId) {
        super(null, EventType.TRIP_DELETE);
        this.tripId = tripId;
    }

    public TripDeleteEvent() {
        super(EventType.TRIP_DELETE);
    }
}
