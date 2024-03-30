package edu.java.client.impl;

import edu.java.client.GitHubClient;
import edu.java.model.response.GitHubPullRequestsResponse;
import edu.java.model.response.GitHubRepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    public GitHubClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GitHubRepositoryResponse fetchRepo(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubRepositoryResponse.class).block();
    }

    @Override
    public GitHubPullRequestsResponse fetchPullRequests(String ownerName, String repositoryName) {
        return webClient.get()
            .uri("/search/issues?q=repo:{ownerName}/{repositoryName}+type:pr", ownerName, repositoryName)
            .retrieve()
            .bodyToMono(GitHubPullRequestsResponse.class).block();
    }
}
