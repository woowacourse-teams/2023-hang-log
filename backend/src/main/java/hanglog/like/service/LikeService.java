package hanglog.like.service;

import hanglog.like.domain.Likes;
import hanglog.like.repository.LikeRepository;
import hanglog.like.dto.request.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
