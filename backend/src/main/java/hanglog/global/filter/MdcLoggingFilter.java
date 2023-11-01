package hanglog.global.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcLoggingFilter implements Filter {

    public static final String REQUEST_ID = "REQUEST_ID";

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        chain.doFilter(request, response);
        MDC.clear();
    }
}
