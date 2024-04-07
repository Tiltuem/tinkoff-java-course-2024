package edu.java.bot.service.kafka;


import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.LinkUpdateService;
import edu.java.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueConsumer {
    private final ApplicationConfig config;
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;
    private final LinkUpdateService linkUpdateService;

    @KafkaListener(topics = "${app.kafka-config.updates-topic.name}",
                   groupId = "updates.listeners",
                   containerFactory = "listenerContainerFactory")
    public void listen(@Payload LinkUpdateRequest request) {
        try {
            linkUpdateService.sendUpdate(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            kafkaTemplate.send(
                config.kafkaConfig().updatesTopic().name() + "_dlq",
                0L,
                request
            );
        }
    }
}
