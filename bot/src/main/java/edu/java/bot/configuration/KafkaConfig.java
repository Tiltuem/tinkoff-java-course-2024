package edu.java.bot.configuration;

import edu.java.dto.LinkUpdateRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {
    public static final String DLQ_SUFFIX = "_dlq";
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
    public NewTopic updatesDlqTopic() {
        return TopicBuilder
            .name(config.kafkaConfig().updatesTopic().name() + DLQ_SUFFIX)
            .partitions(config.kafkaConfig().updatesTopic().partitions())
            .replicas(config.kafkaConfig().updatesTopic().replicas())
            .build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, LinkUpdateRequest> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, LinkUpdateRequest> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfig().servers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Long.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
        )));

        return factory;
    }

    @Bean
    public ProducerFactory<Long, LinkUpdateRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfig().servers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
            JsonSerializer.ADD_TYPE_INFO_HEADERS, false
        ));
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate(
        ProducerFactory<Long, LinkUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
