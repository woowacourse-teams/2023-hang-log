package hanglog.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class PayloadToEventMapper implements Serializable {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Event toObject(final String payload, final Class<? extends Event> event) throws JsonProcessingException {
        return objectMapper.readValue(payload, event);
    }

    public static String toJson(final Event event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }
}
