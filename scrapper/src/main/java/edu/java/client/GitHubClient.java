package edu.java.client;

import edu.java.model.response.GitHubPullRequestsResponse;
import edu.java.model.response.GitHubRepositoryResponse;

public interface GitHubClient {
    GitHubRepositoryResponse fetchRepo(String owner, String repo);

    GitHubPullRequestsResponse fetchPullRequests(String ownerName, String repositoryName);
}
