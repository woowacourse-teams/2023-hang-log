package hanglog.global.filter;

import jakarta.servlet.*;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@Component
public class MdcLoggingFilter implements Filter {

    public static final String REQUEST_ID = "REQUEST_ID";

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        org.jboss.logging.MDC.put("TEST", "testValue");
        Map<String, String> map = MDC.getCopyOfContextMap();
        for (String key : map.keySet()) {
            System.out.println("doFilter : " + map.get(key));
        }
        chain.doFilter(request, response);
        System.out.println("mdc clear!!");
        //MDC.clear();
    }
}
