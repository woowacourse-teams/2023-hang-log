package hanglog.like.domain.repository;

import hanglog.like.domain.Likes;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query("DELETE FROM Likes WHERE tripId IN :tripIds")
    void deleteByTripIds(final Set<Long> tripIds);
}
