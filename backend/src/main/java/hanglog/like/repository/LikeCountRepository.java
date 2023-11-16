package hanglog.like.repository;

import hanglog.like.domain.LikeCount;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LikeCountRepository extends CrudRepository<LikeCount, Long> {

    List<LikeCount> findLikeCountsByTripIdIn(final List<Long> tripIds);
}
