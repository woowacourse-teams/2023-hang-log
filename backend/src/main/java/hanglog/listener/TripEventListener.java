package hanglog.listener;

import hanglog.community.domain.PublishedTrip;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripEvent;
import hanglog.trip.domain.repository.PublishedTripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripEventListener {

    private final PublishedTripRepository publishedTripRepository;

    @EventListener
    public void publishTrip(final TripEvent event) {
        final Trip trip = event.getTrip();
        trip.changePublishedStatus(true);
        if (!publishedTripRepository.existsByTripId(trip.getId())) {
            final PublishedTrip publishedTrip = new PublishedTrip(trip);
            publishedTripRepository.save(publishedTrip);
        }
    }
}
