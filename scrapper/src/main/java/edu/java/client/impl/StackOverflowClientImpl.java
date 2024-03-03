package edu.java.client.impl;

import edu.java.client.StackOverflowClient;
import edu.java.model.StackOverflowQuestionResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public StackOverflowQuestionResponse fetchQuestion(Long id) {
        return webClient.get()
            .uri("/questions/{id}", id)
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class).block();
    }
}
