package hanglog.event;

import static hanglog.event.EventType.TRIP_DELETE;

public class EventMapper {

    public static Event toEvent(final Outbox outbox) {
        final EventType eventType = outbox.getEventType();
        if (eventType.equals(TRIP_DELETE)) {
            return new TripDeleteEvent(outbox.getId(), outbox.getEventType(), outbox.getEventPayload());
        }
        return new TripDeleteEvent(outbox.getId(), outbox.getEventType(), outbox.getEventPayload());
    }
}
