package hanglog.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanglog.event.Event;
import hanglog.event.EventType;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class TripDeleteEvent1 extends Event implements Serializable {

    @JsonProperty
    private Long tripId;

    public TripDeleteEvent1(final Long outboxId, final EventType eventType, final Long tripId) {
        super(outboxId, eventType);
        this.tripId = tripId;
    }

    public TripDeleteEvent1(final Long tripId) {
        super(null, EventType.TRIP_DELETE);
        this.tripId = tripId;
    }

    public TripDeleteEvent1() {
        super(EventType.TRIP_DELETE);
    }
}
