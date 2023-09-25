package hanglog.community.service;

import hanglog.community.domain.Likes;
import hanglog.community.domain.repository.LikesRepository;
import hanglog.community.dto.request.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikesRepository likesRepository;

    public void update(final Long memberId, final Long tripId, final LikeRequest likeRequest) {
        final boolean requestStatus = likeRequest.getIsLike();

        if (requestStatus && !likesRepository.existsByMemberIdAndTripId(memberId, tripId)) {
            likesRepository.save(new Likes(tripId, memberId));
        }
        if (!requestStatus) {
            likesRepository.deleteByMemberIdAndTripId(memberId, tripId);
        }
    }
}
