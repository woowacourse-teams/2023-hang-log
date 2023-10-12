package hanglog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class HangLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangLogApplication.class, args);
    }
}
