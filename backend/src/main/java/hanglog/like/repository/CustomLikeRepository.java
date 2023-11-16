package hanglog.like.repository;

import hanglog.like.domain.Likes;
import java.util.List;

public interface CustomLikeRepository {

    void saveAll(final List<Likes> likes);
}
