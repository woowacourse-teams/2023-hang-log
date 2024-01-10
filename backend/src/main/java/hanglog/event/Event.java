package hanglog.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Event {

    @JsonProperty
    private Long outboxId;

    @JsonProperty
    private EventType eventType;

    public Event(final Long outboxId, final EventType eventType) {
        this.outboxId = outboxId;
        this.eventType = eventType;
    }
}
