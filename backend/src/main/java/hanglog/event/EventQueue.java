package hanglog.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class EventQueue {

    private final Queue<Outbox> queue;
    private final OutboxRepository outboxRepository;
    private final List<OutboxToEventMapper> mappers;

    private final ApplicationEventPublisher publisher;

    @Scheduled(cron = "0 0 * * * *")
    public void offerSavedEvent() {
//        List<Event> events = outboxRepository.findAll().stream().map(outbox -> {
//            try {
//                return getEventFromOutbox(outbox);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();
//        queue.addAll(events);
        queue.addAll(outboxRepository.findAll());
    }

//    @Scheduled(cron = "0 0 * * * *")
//    public void pollEvent() {
//        final Event event = queue.poll();
//        publisher.publishEvent(event);
//    }
    @Scheduled(cron = "0 0 * * * *")
    public void pollEvent() throws JsonProcessingException {
        Outbox outbox = queue.poll();
        OutboxToEventMapper mapper1 = mappers.stream().filter(mapper -> mapper.is(outbox.getEventType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("asdf"));
        publisher.publishEvent(mapper1.toEvent(outbox));
    }

    private Event getEventFromOutbox(final Outbox outbox) throws JsonProcessingException {
        OutboxToEventMapper mapper1 = mappers.stream().filter(mapper -> mapper.is(outbox.getEventType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("asdf"));
        return mapper1.toEvent(outbox);
    }
}
