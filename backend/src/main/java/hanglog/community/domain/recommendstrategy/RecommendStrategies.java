package hanglog.community.domain.recommendstrategy;

import hanglog.global.exception.ExceptionCode;
import hanglog.global.exception.InvalidDomainException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendStrategies {

    private final List<RecommendStrategy> recommendStrategies;

    public RecommendStrategy mapByRecommendType(final RecommendType recommendType) {
        return recommendStrategies.stream()
                .filter(recommendStrategy -> recommendStrategy.isType(recommendType))
                .findFirst()
                .orElseThrow(() -> new InvalidDomainException(ExceptionCode.NOT_FOUND_RECOMMEND_TRIP_STRATEGY));
    }
}
