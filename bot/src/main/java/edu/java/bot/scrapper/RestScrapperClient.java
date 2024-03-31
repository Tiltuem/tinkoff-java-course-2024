package edu.java.bot.scrapper;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.ClientErrorResponse;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import edu.java.exception.CustomClientException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestScrapperClient implements ScrapperClient {
    private static final String LINKS_ENDPOINT = "/links/";
    private static final String CHAT_ENDPOINT_PREFIX = "/tg-chat/";
    private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private final WebClient webClient;
    @Autowired
    private RetryTemplate retryTemplate;

    public RestScrapperClient(@Value("${client.scrapper.base-url:http://localhost:8080}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Mono<Void> registerChat(Long chatId) {
        return retryTemplate.execute(ctx -> webClient.post()
            .uri(CHAT_ENDPOINT_PREFIX + ADD + "/" + chatId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(ClientErrorResponse.class)
                    .flatMap(clientErrorResponse ->
                        Mono.error(new CustomClientException(clientErrorResponse))))
            .bodyToMono(Void.class));
    }

    @Override
    public Mono<Void> deleteChat(Long chatId) {
        return retryTemplate.execute(ctx -> webClient.delete()
            .uri(CHAT_ENDPOINT_PREFIX + DELETE + "/" + chatId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(ClientErrorResponse.class)
                    .flatMap(clientErrorResponse ->
                        Mono.error(new CustomClientException(clientErrorResponse))))
            .bodyToMono(Void.class));
    }

    @Override
    public Mono<ListLinksResponse> getAllListLinks(Long chatId) {
        return retryTemplate.execute(ctx -> webClient.get()
            .uri(LINKS_ENDPOINT + "/get-all")
            .header(TG_CHAT_ID_HEADER, chatId.toString())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(ClientErrorResponse.class)
                    .flatMap(clientErrorResponse ->
                        Mono.error(new CustomClientException(clientErrorResponse))))
            .bodyToMono(ListLinksResponse.class));
    }

    @Override
    public Mono<LinkResponse> addLink(Long chatId, URI link) {
        return retryTemplate.execute(ctx -> webClient.post()
            .uri(LINKS_ENDPOINT + ADD)
            .header(TG_CHAT_ID_HEADER, chatId.toString())
            .bodyValue(new AddLinkRequest(link.toString()))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(ClientErrorResponse.class)
                    .flatMap(clientErrorResponse ->
                        Mono.error(new CustomClientException(clientErrorResponse))))
            .bodyToMono(LinkResponse.class));
    }

    @Override
    public Mono<LinkResponse> removeLink(Long chatId, URI link) {
        return retryTemplate.execute(ctx -> webClient.method(HttpMethod.DELETE)
            .uri(LINKS_ENDPOINT + DELETE)
            .header(TG_CHAT_ID_HEADER, chatId.toString())
            .body(Mono.just(new RemoveLinkRequest(link)), RemoveLinkRequest.class)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(ClientErrorResponse.class)
                    .flatMap(clientErrorResponse ->
                        Mono.error(new CustomClientException(clientErrorResponse))))
            .bodyToMono(LinkResponse.class));
    }
}
