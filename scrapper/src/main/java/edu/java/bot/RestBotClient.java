package edu.java.bot;

import edu.java.dto.ClientErrorResponse;
import edu.java.dto.LinkUpdateRequest;
import edu.java.exception.CustomClientException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestBotClient implements BotClient {
    private static final String UPDATES_ENDPOINT = "/updates";
    private final WebClient webClient;
    @Autowired
    @Qualifier("bot")
    protected RetryTemplate retryTemplate;

    @Autowired
    public RestBotClient(@Value("${client.bot.base-url:http://localhost:8090}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Mono<Void> sendUpdates(Long id, String url, String description, List<Long> tgChatIds) {
        return retryTemplate.execute(ctx -> webClient.post()
            .uri(UPDATES_ENDPOINT)
            .bodyValue(new LinkUpdateRequest(id, url, description, tgChatIds))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(ClientErrorResponse.class)
                    .flatMap(clientErrorResponse ->
                        Mono.error(new CustomClientException(clientErrorResponse))))
            .bodyToMono(Void.class));
    }
}
