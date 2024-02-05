package hanglog.like.domain.repository;

import hanglog.like.domain.Likes;
import hanglog.like.dto.LikeElement;
import java.util.List;
import java.util.Optional;

public interface CustomLikeRepository {

    void saveAll(final List<Likes> likes);

    Optional<LikeElement> findLikesElementByTripId(final Long tripId);

    List<LikeElement> findLikeElementByTripIds(final List<Long> tripIds);
}
