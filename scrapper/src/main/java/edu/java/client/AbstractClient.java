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
        String path = startPath + "/%s".repeat(objects.length);
        path = String.format(path, objects);
        return webClient.get().uri(path).retrieve().bodyToMono(response);
    }
}
