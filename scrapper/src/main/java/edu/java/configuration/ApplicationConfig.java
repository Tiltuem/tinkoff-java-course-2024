package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    Retries retry,
    @NotNull
    KafkaConfig kafkaConfig,
    @NotNull
    Boolean useQueue
) {
    public record Scheduler(boolean enable, @NotNull Duration invokeInterval, @NotNull Duration forceCheckDelay,
                            @NotNull Duration checkInterval) {
    }

    public enum AccessType {
        JDBC,
        JOOQ,
        JPA
    }

    public record Retries(Retry scrapper, Retry bot) {
        public record Retry(
            Set<Integer> httpStatuses,
            Integer maxAttempts,
            RetryType type,
            RetryConfig config
        ) {
            public enum RetryType {
                CONSTANT, LINEAR, EXPONENTIAL
            }

            public record RetryConfig(
                Long initialIntervalMillis,
                Long maxIntervalMillis,
                Double multiplier
            ) {
            }
        }
    }

    public record KafkaConfig(
        String servers,
        UpdatesTopic updatesTopic
    ) {
        public record UpdatesTopic(
            String name,
            Integer partitions,
            Integer replicas
        ) {
        }
    }
}
