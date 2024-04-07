package edu.java.configuration;

import edu.java.dto.LinkUpdateRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {
    private final ApplicationConfig config;

    @Bean
    public NewTopic updatesTopic() {
        return TopicBuilder
            .name(config.kafkaConfig().updatesTopic().name())
            .partitions(config.kafkaConfig().updatesTopic().partitions())
            .replicas(config.kafkaConfig().updatesTopic().replicas())
            .build();
    }

    @Bean
    ProducerFactory<Long, LinkUpdateRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfig().servers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Long.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
            JsonSerializer.ADD_TYPE_INFO_HEADERS, false
        ));
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdateRequest> linkUpdatesKafkaTemplate(
        ProducerFactory<Long, LinkUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
