package hanglog.like.repository;

import hanglog.like.domain.MemberLike;
import org.springframework.data.repository.CrudRepository;

public interface MemberLikeRepository extends CrudRepository<MemberLike, Long> {
}
