package hanglog.test;

import hanglog.login.domain.RefreshToken;
import hanglog.login.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestEventListener {

    private final RefreshTokenRepository refreshTokenRepository;

    @Async
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    @TransactionalEventListener
    public void legendListener(final TestEvent event) {
        try {
            log.info("리스너 시작");
            System.out.println("리스너 시작");
            refreshTokenRepository.save(new RefreshToken("token", 1L));

        } catch (Exception e) {
            throw new IllegalArgumentException("리스너 예외 발생");
        }
//            throw new IllegalArgumentException();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        throw new IllegalArgumentException();
    }
}
