package hanglog.community.domain.recommendstrategy;

import hanglog.trip.domain.Trip;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RecommendStrategy {

    List<Trip> recommend(Pageable pageable);
}
