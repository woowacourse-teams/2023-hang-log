package filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(@NonNull final HttpServletRequest request, @NonNull final String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(@NonNull final HttpServletRequest request, @NonNull final String message) {
        logger.info(message);
    }
}
