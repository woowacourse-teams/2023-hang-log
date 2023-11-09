package hanglog.test;

import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final ApplicationEventPublisher publisher;

    private final MemberRepository memberRepository;

    @Transactional
    public void legend() {
        memberRepository.save(new Member(1L, "dasdsd", "dino", "https://hanglog.image/sada"));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {

            publisher.publishEvent(new TestEvent(1));
        } catch (Exception e) {
            throw new IllegalArgumentException("서비스로 에러 전파됨");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
