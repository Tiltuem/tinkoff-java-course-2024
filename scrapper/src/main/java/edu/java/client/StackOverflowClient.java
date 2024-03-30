package edu.java.client;

import edu.java.model.response.StackOverflowQuestionItemResponse;
import java.util.Optional;

public interface StackOverflowClient {
    Optional<StackOverflowQuestionItemResponse> fetchQuestion(Long id);
}
