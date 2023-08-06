package hanglog.config;

import filter.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingConfig {

    public static int MAX_PAYLOAD_LENGTH = 1000;

    @Bean
    public RequestLoggingFilter loggingFilter() {
        RequestLoggingFilter filter = new RequestLoggingFilter();
        filter.setIncludeClientInfo(false);
        filter.setIncludeHeaders(false);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        filter.setMaxPayloadLength(MAX_PAYLOAD_LENGTH);
        return filter;
    }
}
