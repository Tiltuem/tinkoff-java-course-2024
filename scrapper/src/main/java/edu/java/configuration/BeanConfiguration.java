package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.GitHubClientImpl;
import edu.java.client.impl.StackOverflowClientImpl;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public GitHubClient gitHubClient(@Value("${github.base.url}") String baseUrl) {
        return new GitHubClientImpl(baseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(@Value("${stackOverflow.base.url}") String baseUrl) {
        return new StackOverflowClientImpl(baseUrl);
    }

    @Bean
    public Duration schedulerDelay(ApplicationConfig applicationConfig) {
        return applicationConfig.scheduler().interval();
    }
}
