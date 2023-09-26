package hanglog.community.service;

import hanglog.community.domain.Likes;
import hanglog.community.domain.repository.LikeRepository;
import hanglog.community.dto.request.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;

    public void update(final Long memberId, final Long tripId, final LikeRequest likeRequest) {
        final boolean requestStatus = likeRequest.getIsLike();

        if (requestStatus && !likeRepository.existsByMemberIdAndTripId(memberId, tripId)) {
            likeRepository.save(new Likes(tripId, memberId));
        }
        if (!requestStatus) {
            likeRepository.deleteByMemberIdAndTripId(memberId, tripId);
        }
    }
}
