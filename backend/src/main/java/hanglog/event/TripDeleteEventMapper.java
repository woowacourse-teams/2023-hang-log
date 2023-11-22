package hanglog.event;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TripDeleteEventMapper implements OutboxToEventMapper {

    @Override
    public boolean is(final EventType type) {
        return type.equals(EventType.TRIP_DELETE);
    }

    @Override
    public TripDeleteEvent1 toEvent(final Outbox outbox) throws JsonProcessingException {
        String eventPayload = outbox.getEventPayload();
        TripDeleteEvent1 event1 = (TripDeleteEvent1) PayloadToEventMapper.toObject(eventPayload, TripDeleteEvent1.class);
        return new TripDeleteEvent1(outbox.getId(), outbox.getEventType(), event1.getTripId());
    }
}
