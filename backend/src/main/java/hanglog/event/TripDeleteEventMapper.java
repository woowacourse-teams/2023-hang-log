package hanglog.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public class TripDeleteEventMapper implements OutboxToEventMapper {

    @Override
    public boolean is(final EventType type) {
        return type.equals(EventType.TRIP_DELETE);
    }

    @Override
    public TripDeleteEvent toEvent(final Outbox outbox) throws JsonProcessingException {
        final String eventPayload = outbox.getEventPayload();
        final TripDeleteEvent tripDeleteEvent = (TripDeleteEvent) PayloadToEventMapper.toObject(eventPayload, TripDeleteEvent.class);
        return new TripDeleteEvent(outbox.getId(), outbox.getEventType(), tripDeleteEvent.getTripId());
    }
}
