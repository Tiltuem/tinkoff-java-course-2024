package edu.java.bot;

import edu.java.dto.LinkUpdateRequest;
import reactor.core.publisher.Mono;

public interface BotClient {
    Mono<Void> sendUpdates(LinkUpdateRequest request);
}
