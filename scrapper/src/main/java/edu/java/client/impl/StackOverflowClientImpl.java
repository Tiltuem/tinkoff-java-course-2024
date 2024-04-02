package edu.java.client.impl;

import edu.java.client.StackOverflowClient;
import edu.java.model.response.StackOverflowQuestionItemResponse;
import edu.java.model.response.StackOverflowQuestionResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;
    @Autowired
    @Qualifier("scrapper")
    private RetryTemplate retryTemplate;

    public StackOverflowClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Optional<StackOverflowQuestionItemResponse> fetchQuestion(Long id) {
        return retryTemplate.execute(ctx -> webClient.get()
            .uri("/questions/{id}", id)
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class)
            .map(resp -> Optional.ofNullable(!resp.items().isEmpty() ? resp.items().getFirst() : null)).block());
    }
}
