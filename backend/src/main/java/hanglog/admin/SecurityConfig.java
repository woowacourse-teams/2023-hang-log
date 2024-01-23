package hanglog.admin;

import hanglog.admin.infrastructure.BCryptPasswordEncoder;
import hanglog.admin.infrastructure.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
