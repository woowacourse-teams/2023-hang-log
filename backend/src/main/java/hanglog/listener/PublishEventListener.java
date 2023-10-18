package hanglog.listener;

import hanglog.community.domain.PublishedTrip;
import hanglog.community.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.PublishedDeleteEvent;
import hanglog.trip.domain.PublishedEvent;
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

    @EventListener
    public void publishDeleteTrip(final PublishedDeleteEvent publishedDeleteEvent) {
        publishedTripRepository.deleteByTripId(publishedDeleteEvent.getTripId());
    }
}
