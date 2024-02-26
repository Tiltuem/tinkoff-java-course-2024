package edu.java.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class AbstractClient<T> {
    protected final WebClient webClient;
    protected final String startPath;
    protected final Class<T> response;

    protected AbstractClient(String baseUrl, String startPath, Class<T> response) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.startPath = startPath;
        this.response = response;
    }

    public Mono<T> fetch(String... objects) {
        return webClient.get().uri(String.format(startPath + "/%s".repeat(objects.length), objects)).retrieve()
            .bodyToMono(response);
    }
}
