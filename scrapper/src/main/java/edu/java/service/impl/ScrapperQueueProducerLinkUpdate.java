package edu.java.service.impl;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class ScrapperQueueProducerLinkUpdate implements LinkUpdateService {
    private final ApplicationConfig config;
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        kafkaTemplate.send(config.kafkaConfig().updatesTopic().name(), request.id(), request);
    }
}
