package hanglog.community.domain.recommendstrategy;

import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.TripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RecommendStrategies {

    public static RecommendStrategies LIKE(final TripRepository tripRepository) {
        return new RecommendStrategies(
                "지금 인기있는 여행들이에요",
                new LikesRecommendStrategy(tripRepository)
        );
    }

    private final String title;
    private final RecommendStrategy recommendStrategy;

    public List<Trip> recommend(final Pageable pageable) {
        return this.recommendStrategy.recommend(pageable);
    }

    public String getTitle() {
        return this.title;
    }


}
