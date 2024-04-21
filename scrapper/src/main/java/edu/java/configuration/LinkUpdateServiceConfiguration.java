package edu.java.configuration;

import edu.java.bot.BotClient;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkUpdateService;
import edu.java.service.impl.RestClientLinkUpdate;
import edu.java.service.impl.ScrapperQueueProducerLinkUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
public class LinkUpdateServiceConfiguration {
    private final ApplicationConfig applicationConfig;
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;
    private final BotClient botClient;

    public LinkUpdateService scrapperQueueProducer() {
        return applicationConfig.useQueue() ? new ScrapperQueueProducerLinkUpdate(
            applicationConfig,
            kafkaTemplate
        ) : new RestClientLinkUpdate(botClient);
    }
}
