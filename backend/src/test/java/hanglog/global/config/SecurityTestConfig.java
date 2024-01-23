package hanglog.global.config;

import hanglog.admin.infrastructure.BCryptPasswordEncoder;
import hanglog.admin.infrastructure.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
