package hanglog.like.domain.repository;

import hanglog.like.domain.Likes;
import java.util.List;

public interface CustomLikeRepository {

    void saveAll(final List<Likes> likes);
}
