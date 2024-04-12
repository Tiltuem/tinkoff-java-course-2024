package edu.java.bot.configuration;

import edu.java.dto.LinkUpdateRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

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
    public ConcurrentKafkaListenerContainerFactory<Long, LinkUpdateRequest> listenerContainerFactory(
        ConsumerFactory<Long, LinkUpdateRequest> consumerFactory,
        @Qualifier("dlq")
        KafkaTemplate<?, ?> kafkaDlqTemplate
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, LinkUpdateRequest>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(new DefaultErrorHandler(
            new DeadLetterPublishingRecoverer(
                kafkaDlqTemplate,
                (consumerRecord, e) -> new TopicPartition(
                    config.kafkaConfig().updatesTopic().name() + DLQ_SUFFIX,
                    consumerRecord.partition()
                )
            ),
            new FixedBackOff(0L, 0L)
        ));
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

    @Bean
    @Qualifier("dlq")
    public KafkaTemplate<?, ?> kafkaDlqTemplate() {
        var factory = new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfig().servers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            JsonSerializer.ADD_TYPE_INFO_HEADERS, false
        ));
        return new KafkaTemplate<>(factory);
    }
}
