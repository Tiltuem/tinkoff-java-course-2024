package edu.java.configuration;


import edu.java.retry.RetryTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;


@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {
    private final ApplicationConfig config;

    @Bean
    @Qualifier("scrapper")
    public RetryTemplate linksRetryTemplate() {
        return retryTemplate(RetryCategory.SCRAPPER);
    }

    @Bean
    @Qualifier("bot")
    public RetryTemplate botRetryTemplate() {
        return retryTemplate(RetryCategory.BOT);
    }

    public RetryTemplate retryTemplate(RetryCategory category) {
        var retry = switch (category) {
            case SCRAPPER -> config.retry().scrapper();
            case BOT -> config.retry().bot();
        };
        return switch (retry.type()) {
            case CONSTANT -> RetryTemplates.constant(
                retry.httpStatuses(),
                retry.maxAttempts(),
                retry.config().initialIntervalMillis()
            );
            case LINEAR -> RetryTemplates.linear(
                retry.httpStatuses(),
                retry.maxAttempts(),
                retry.config().initialIntervalMillis(),
                retry.config().maxIntervalMillis()
            );
            case EXPONENTIAL -> RetryTemplates.exponential(
                retry.httpStatuses(),
                retry.maxAttempts(),
                retry.config().initialIntervalMillis(),
                retry.config().multiplier(),
                retry.config().maxIntervalMillis()
            );
        };
    }

    public enum RetryCategory {
        SCRAPPER, BOT
    }
}
