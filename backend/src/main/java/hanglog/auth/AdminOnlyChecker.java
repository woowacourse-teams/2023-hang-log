package hanglog.auth;

import static hanglog.global.exception.ExceptionCode.INVALID_ADMIN_AUTHORITY;

import hanglog.auth.domain.Accessor;
import hanglog.global.exception.AdminException;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminOnlyChecker {

    @Before("@annotation(hanglog.auth.AdminOnly)")
    public void check(final JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(Accessor::isAdmin)
                .findFirst()
                .orElseThrow(() -> new AdminException(INVALID_ADMIN_AUTHORITY));
    }
}
