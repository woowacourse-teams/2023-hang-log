package hanglog.global;

import java.io.IOException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SlackSenderAop {

    private final SlackMessageSender sender;

    public SlackSenderAop(final SlackMessageSender sender) {
        this.sender = sender;
    }

    @After("@annotation(hanglog.global.SlackNotification)")
    public void logging(final JoinPoint joinPoint) throws IOException {
        sender.send((Exception) joinPoint.getArgs()[0]);
    }
}
