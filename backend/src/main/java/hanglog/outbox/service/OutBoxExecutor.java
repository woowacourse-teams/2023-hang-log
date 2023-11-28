package hanglog.outbox.service;

import hanglog.outbox.domain.OutBox;
import hanglog.trip.domain.TripDeleteEvent;
import hanglog.outbox.domain.repository.OutBoxRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OutBoxExecutor {

    private final OutBoxRepository outBoxRepository;
    private final ApplicationEventPublisher publisher;

    @Scheduled(cron = "*/2 * * * * *")
    public void deleteUnprocessedTrip() {
        final List<OutBox> outBoxes = outBoxRepository.findAll();
        for (final OutBox outBox : outBoxes) {
            if (outBox.isTrip()) {
                publisher.publishEvent(new TripDeleteEvent(outBox.getTargetId()));
            }
        }
    }
}
