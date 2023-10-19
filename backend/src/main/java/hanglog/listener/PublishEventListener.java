package hanglog.listener;

import hanglog.community.domain.PublishedTrip;
import hanglog.community.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.PublishDeleteEvent;
import hanglog.trip.domain.PublishEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishEventListener {

    private final PublishedTripRepository publishedTripRepository;

    @EventListener
    public void publishTrip(final PublishEvent publishEvent) {
        final Long tripId = publishEvent.getTripId();

        if (!publishedTripRepository.existsByTripId(tripId)) {
            final PublishedTrip publishedTrip = new PublishedTrip(tripId);
            publishedTripRepository.save(publishedTrip);
        }
    }

    @EventListener
    public void publishDeleteTrip(final PublishDeleteEvent publishDeleteEvent) {
        publishedTripRepository.deleteByTripId(publishDeleteEvent.getTripId());
    }
}
