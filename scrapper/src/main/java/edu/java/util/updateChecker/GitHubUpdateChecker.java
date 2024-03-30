package edu.java.util.updateChecker;

import edu.java.client.GitHubClient;
import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.model.response.GitHubPullRequestsResponse;
import edu.java.model.response.GitHubRepositoryResponse;
import edu.java.repository.GithubLinkRepository;
import edu.java.service.updater.GithubLinkUpdater;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubUpdateChecker<T extends GithubLinkRepository> implements UpdateChecker {
    private static final String HOST = "github.com";
    private final GitHubClient gitHubClient;
    private final GithubLinkUpdater<T> linkUpdater;

    @Override
    public Optional<String> checkUpdates(Link link) {
        String[] segments = link.getUrl().getPath().split("/");
        String ownerName = segments[1];
        String repositoryName = segments[2];
        GitHubRepositoryResponse response = gitHubClient.fetchRepo(ownerName, repositoryName);

        GitHubPullRequestsResponse pullRequestsResponse = gitHubClient.fetchPullRequests(ownerName, repositoryName);

        GithubLinkInfo linkInfo = new GithubLinkInfo(
            link,
            Optional.ofNullable(response).map(GitHubRepositoryResponse::updatedAt),
            Objects.requireNonNull(pullRequestsResponse).pullRequestsCount()
        );
        return linkUpdater.update(linkInfo);
    }

    @Override
    public boolean isAppropriateLink(Link link) {
        return link.getUrl().getHost().equals(HOST);
    }
}
