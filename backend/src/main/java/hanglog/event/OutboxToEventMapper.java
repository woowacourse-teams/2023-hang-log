package hanglog.event;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OutboxToEventMapper<T extends Event> {

    boolean is(EventType type);
    T toEvent(Outbox outbox) throws JsonProcessingException;
}
