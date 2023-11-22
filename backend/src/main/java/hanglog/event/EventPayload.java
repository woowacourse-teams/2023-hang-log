package hanglog.event;

import java.util.Map;


public class EventPayload {

    private final Map<String, Object> payload;

    public EventPayload(final Map<String, Object> payload) {
        this.payload = payload;
    }
}
