package hanglog.global.detector;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
public class QueryCounterAop {

    private final ThreadLocal<LoggingForm> currentLoggingForm;

    public QueryCounterAop() {
        this.currentLoggingForm = new ThreadLocal<>();
    }

    @Around("execution( * javax.sql.DataSource.getConnection())")
    public Object captureConnection(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object connection = joinPoint.proceed();

        return new ConnectionProxyHandler(connection, getCurrentLoggingForm()).getProxy();
    }

    private LoggingForm getCurrentLoggingForm() {
        if (currentLoggingForm.get() == null) {
            currentLoggingForm.set(new LoggingForm());
        }

        return currentLoggingForm.get();
    }

    @After("within(@org.springframework.web.bind.annotation.RestController *)")
    public void loggingAfterApiFinish() {
        final LoggingForm loggingForm = getCurrentLoggingForm();

        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (isInRequestScope(attributes)) {
            final HttpServletRequest request = attributes.getRequest();

            loggingForm.setApiMethod(request.getMethod());
            loggingForm.setApiUrl(request.getRequestURI());
        }

        log.info("{}", getCurrentLoggingForm());
        currentLoggingForm.remove();
    }

    private boolean isInRequestScope(final ServletRequestAttributes attributes) {
        return attributes != null;
    }
}
