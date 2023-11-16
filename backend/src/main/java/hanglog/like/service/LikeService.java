package hanglog.like.service;

import hanglog.like.domain.MemberLike;
import hanglog.like.dto.MemberLikeCacheEvent;
import hanglog.like.dto.request.LikeRequest;
import hanglog.like.repository.MemberLikeRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final MemberLikeRepository memberLikeRepository;
    private final ApplicationEventPublisher publisher;

    public void update(final Long memberId, final Long tripId, final LikeRequest likeRequest) {
        Map<Long, Boolean> tripLikeStatusMap = new HashMap<>();
        final Optional<MemberLike> memberLike = memberLikeRepository.findById(memberId);
        if (memberLike.isPresent()) {
            tripLikeStatusMap = memberLike.get().getTripLikeStatusMap();
        }
        tripLikeStatusMap.put(tripId, likeRequest.getIsLike());
        memberLikeRepository.save(new MemberLike(memberId, tripLikeStatusMap));
    }

    public boolean check(final Long memberId, final Long tripId) {
        Optional<MemberLike> memberLike = memberLikeRepository.findById(memberId);
        if (memberLike.isPresent()) {
            return memberLike.get().getTripLikeStatusMap().get(tripId);
        }
        return false;
    }

    @Scheduled(fixedRate = 3600000)
    public void writeBackMemberLikeCache() {
        publisher.publishEvent(new MemberLikeCacheEvent());
    }
}
