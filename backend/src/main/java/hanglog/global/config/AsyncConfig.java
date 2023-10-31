package hanglog.global.config;

import hanglog.global.filter.MdcTaskDecorator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor(@Qualifier("applicationTaskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        taskExecutor.setTaskDecorator(new MdcTaskDecorator());
        return taskExecutor;
    }
}
