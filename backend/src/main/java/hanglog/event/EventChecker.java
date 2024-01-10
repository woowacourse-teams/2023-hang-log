package hanglog.event;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class EventChecker {

    private final OutboxRepository outboxRepository;
    private final EventRetryExecutor eventRetryExecutor;

    @AfterReturning("@annotation(org.springframework.transaction.event.TransactionalEventListener)")
    public void checkSuccess(final JoinPoint joinPoint) {
        final Event event = castToEvent(joinPoint);
        outboxRepository.deleteById(event.getOutboxId());
    }

    @AfterThrowing("@annotation(org.springframework.transaction.event.TransactionalEventListener)")
    public void checkFail(final JoinPoint joinPoint) {
        final Event event = castToEvent(joinPoint);
//        outboxRepository.updateStatusToRetry();
        eventRetryExecutor.add(event);
    }

    private Event castToEvent(final JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(Event.class::isInstance)
                .map(Event.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Event 타입이 아님."));
    }
}
