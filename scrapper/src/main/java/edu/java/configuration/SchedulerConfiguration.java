package edu.java.configuration;

import edu.java.service.LinkUpdaterScheduler;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {
    @Bean
    public Duration schedulerDelay(ApplicationConfig applicationConfig) {
        return applicationConfig.scheduler().interval();
    }

    @Bean
    public LinkUpdaterScheduler linkUpdaterScheduler() {
        return new LinkUpdaterScheduler();
    }
}
