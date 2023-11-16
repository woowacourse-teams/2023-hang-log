package hanglog.like.service;

import hanglog.like.domain.LikeCount;
import hanglog.like.domain.Likes;
import hanglog.like.domain.MemberLike;
import hanglog.like.dto.TripLikeCount;
import hanglog.like.dto.request.LikeRequest;
import hanglog.like.repository.CustomLikeRepository;
import hanglog.like.repository.LikeCountRepository;
import hanglog.like.repository.LikeRepository;
import hanglog.like.repository.MemberLikeRepository;
import hanglog.member.domain.repository.MemberRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberLikeRepository memberLikeRepository;
    private final LikeCountRepository likeCountRepository;
    private final MemberRepository memberRepository;
    private final CustomLikeRepository customLikeRepository;

    public void update(final Long memberId, final Long tripId, final LikeRequest likeRequest) {
        Map<Long, Boolean> tripLikeStatusMap = new HashMap<>();
        final Optional<MemberLike> memberLike = memberLikeRepository.findById(memberId);
        if (memberLike.isPresent()) {
            tripLikeStatusMap = memberLike.get().getTripLikeStatusMap();
        }
        tripLikeStatusMap.put(tripId, likeRequest.getIsLike());
        memberLikeRepository.save(new MemberLike(memberId, tripLikeStatusMap));
        updateLikeCountCache(tripId, likeRequest);
    }

    private void updateLikeCountCache(final Long tripId, final LikeRequest likeRequest) {
        final Optional<LikeCount> likeCount = likeCountRepository.findById(tripId);
        if (Boolean.TRUE.equals(likeRequest.getIsLike())) {
            likeCount.ifPresent(count -> likeCountRepository.save(new LikeCount(tripId, count.getCount() + 1)));
            return;
        }
        likeCount.ifPresent(count -> likeCountRepository.save(new LikeCount(tripId, count.getCount() - 1)));
    }

    @Scheduled(cron = "0 0 * * * *")
    public void writeBackMemberLikeCache() {
        final List<Likes> likes = memberRepository.findAll().stream()
                .flatMap(member -> memberLikeRepository.findById(member.getId())
                        .map(memberLike -> memberLike.getTripLikeStatusMap()
                                .entrySet().stream()
                                .filter(Map.Entry::getValue)
                                .map(entry -> new Likes(entry.getKey(), member.getId())))
                        .orElseGet(Stream::empty))
                .toList();
        customLikeRepository.saveAll(likes);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cacheLikeCount() {
        final List<TripLikeCount> tripLikeCounts = likeRepository.findCountByAllTrips();
        for (final TripLikeCount tripLikeCount : tripLikeCounts) {
            likeCountRepository.save(new LikeCount(tripLikeCount.getTripId(), tripLikeCount.getCount()));
        }
    }
}
