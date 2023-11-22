package hanglog.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.LinkedList;
import java.util.List;
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
    private final List<OutboxToEventMapper> mappers;

    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedRate = 2000)
    public void offerSavedEvent() {
        queue.addAll(outboxRepository.findAll());
    }

    @Scheduled(fixedRate = 2000)
    public void pollEvent() throws JsonProcessingException {
        if (queue.isEmpty()) {
            return;
        }

        final Outbox outbox = queue.poll();
        final OutboxToEventMapper outboxToEventMapper = mappers.stream().filter(mapper -> mapper.is(outbox.getEventType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이벤트 타입에 해당하는 매퍼가 없어요오"));
        publisher.publishEvent(outboxToEventMapper.toEvent(outbox));
    }
}
