package hanglog.event;

import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventQueue {

    private final Queue<Outbox> queue = new LinkedList<>();
    private final OutboxRepository outboxRepository;

    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedRate = 2000)
    public void offerSavedEvent() {
        queue.addAll(outboxRepository.findAll());
    }

    @Scheduled(fixedRate = 2000)
    public void pollEvent() {
        if (queue.isEmpty()) {
            return;
        }

        final Outbox outbox = queue.poll();
        publisher.publishEvent(EventMapper.toEvent(outbox));
    }
}
