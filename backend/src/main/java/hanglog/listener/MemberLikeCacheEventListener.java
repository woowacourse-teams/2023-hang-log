package hanglog.listener;

import hanglog.like.domain.Likes;
import hanglog.like.dto.MemberLikeCacheEvent;
import hanglog.like.repository.CustomLikeRepository;
import hanglog.like.repository.MemberLikeRepository;
import hanglog.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberLikeCacheEventListener {

    private final MemberRepository memberRepository;
    private final CustomLikeRepository customLikeRepository;
    private final MemberLikeRepository memberLikeRepository;

    @EventListener
    public void writeBack(final MemberLikeCacheEvent event) {
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
}
