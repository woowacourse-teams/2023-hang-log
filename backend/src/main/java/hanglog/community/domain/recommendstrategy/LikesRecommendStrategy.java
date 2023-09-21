package hanglog.community.domain.recommendstrategy;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class LikesRecommendStrategy implements RecommendStrategy {

    private final TripRepository tripRepository;

    @Override
    public List<Trip> recommend(final Pageable pageable) {
        return tripRepository.findTripsOrderByLikesCount(pageable);
    }
}
