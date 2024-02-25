package edu.java.client;

import edu.java.model.GitHubResponse;

public class GitHubClient extends AbstractClient<GitHubResponse> {
    public GitHubClient(String baseUrl) {
        super(baseUrl, "/repos", GitHubResponse.class);
    }
}
