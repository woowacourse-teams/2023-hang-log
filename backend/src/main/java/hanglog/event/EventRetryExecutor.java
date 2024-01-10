package hanglog.event;

import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EventRetryExecutor {

    private final Queue<Event> queue = new LinkedList<>();
    private final RetryEventCountRepository retryEventCountRepository;
    private final ApplicationEventPublisher publisher;

    public void add(final Event event) {
        queue.add(event);
    }

    @Transactional
    @Scheduled(cron = "0 0/1 3-5 * * *")
    public void poll() {
        if (queue.isEmpty()) {
            return;
        }

        final Event event = queue.poll();
        final Long outboxId = event.getOutboxId();
        final RetryEventCount retryEventCount = retryEventCountRepository.findById(outboxId)
                .orElseGet(() -> new RetryEventCount(outboxId, 0));
        retryEventCount.increase();
        retryEventCountRepository.save(retryEventCount);

        if (retryEventCount.isMax()) {
            return ;
        }
        publisher.publishEvent(event);
    }
}
