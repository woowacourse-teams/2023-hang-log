package hanglog.global.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.UUID;

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
