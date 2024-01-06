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

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    public void offerSavedEvent() {
        queue.addAll(outboxRepository.findAll());
    }

    // 매일 3시00분-4시55분 사이에 1분 간격으로 실행
    @Scheduled(cron = "0 0/1 3-5 * * *")
    public void pollEvent() {
        if (queue.isEmpty()) {
            return;
        }

        final Outbox outbox = queue.poll();
        publisher.publishEvent(EventMapper.toEvent(outbox));
    }
}
