package hanglog.outbox.service;

import hanglog.member.domain.MemberDeleteEvent;
import hanglog.outbox.domain.OutBox;
import hanglog.outbox.domain.repository.OutBoxRepository;
import hanglog.trip.domain.TripDeleteEvent;
import hanglog.trip.domain.repository.CustomTripRepository;
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
    private final CustomTripRepository customTripRepository;
    private final ApplicationEventPublisher publisher;

    @Scheduled(cron = "*/2 * * * * *")
    public void execute() {
        final List<OutBox> outBoxes = outBoxRepository.findAll();
        outBoxes.forEach(outBox -> {
            if (outBox.isMember()) {
                final List<Long> tripIds = customTripRepository.findTripIdsByMemberId(outBox.getTargetId());
                publisher.publishEvent(new MemberDeleteEvent(tripIds, outBox.getTargetId()));
            }
            if (outBox.isTrip()) {
                publisher.publishEvent(new TripDeleteEvent(outBox.getTargetId()));
            }
        });
    }
}
