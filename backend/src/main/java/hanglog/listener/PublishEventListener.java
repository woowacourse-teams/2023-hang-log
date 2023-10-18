package hanglog.listener;

import hanglog.community.domain.PublishedEvent;
import hanglog.community.domain.PublishedTrip;
import hanglog.trip.domain.repository.PublishedTripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishEventListener {

    private final PublishedTripRepository publishedTripRepository;

    @EventListener
    public void publishTrip(final PublishedEvent publishedEvent) {
        final Long tripId = publishedEvent.getTripId();

        if (!publishedTripRepository.existsByTripId(tripId)) {
            final PublishedTrip publishedTrip = new PublishedTrip(tripId);
            publishedTripRepository.save(publishedTrip);
        }
    }
}
