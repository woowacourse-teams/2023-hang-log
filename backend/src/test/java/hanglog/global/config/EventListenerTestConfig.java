package hanglog.global.config;

import hanglog.community.domain.repository.PublishedTripRepository;
import hanglog.listener.PublishEventListener;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EventListenerTestConfig {

    @Bean
    public PublishEventListener publishEventListener(PublishedTripRepository publishedTripRepository) {
        return new PublishEventListener(publishedTripRepository);
    }
}
