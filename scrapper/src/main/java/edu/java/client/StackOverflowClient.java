package edu.java.client;

import edu.java.model.StackOverflowResponse;

public class StackOverflowClient extends AbstractClient<StackOverflowResponse> {
    public StackOverflowClient(String baseUrl) {
        super(baseUrl, "/questions", StackOverflowResponse.class);
    }
}
