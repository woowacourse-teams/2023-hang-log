package hanglog.community.domain.recommendstrategy;

import static hanglog.community.domain.recommendstrategy.RecommendType.LIKE;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikesRecommendStrategy implements RecommendStrategy {

    private static final String TITLE = "지금 인기있는 여행들이에요";

    private final TripRepository tripRepository;

    @Override
    public List<Trip> recommend(final Pageable pageable) {
        return tripRepository.findTripsOrderByLikesCount(pageable);
    }

    @Override
    public boolean isType(final RecommendType recommendType) {
        return LIKE.equals(recommendType);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
