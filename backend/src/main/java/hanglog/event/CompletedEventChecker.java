package hanglog.event;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class CompletedEventChecker {

    private final OutboxRepository outboxRepository;

    @AfterReturning("@annotation(org.springframework.transaction.event.TransactionalEventListener)")
    public void check(final JoinPoint joinPoint) {
        System.out.println("CompletedEventChecker.check");
        final Event event = Arrays.stream(joinPoint.getArgs())
                .filter(Event.class::isInstance)
                .map(Event.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Event 타입이 아님."));
        outboxRepository.deleteById(event.getOutboxId());
    }
}
