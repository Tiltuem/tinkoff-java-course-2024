package edu.java.client;

import edu.java.model.GitHubRepositoryResponse;

public interface GitHubClient {
    GitHubRepositoryResponse fetchRepo(String owner, String repo);
}
