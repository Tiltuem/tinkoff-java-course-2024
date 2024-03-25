package edu.java.util.updateChecker;

import edu.java.client.GitHubClient;
import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.model.info.LinkInfo;
import edu.java.model.response.GitHubPullRequestsResponse;
import edu.java.model.response.GitHubRepositoryResponse;
import edu.java.service.LinkUpdater;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitHubUpdateChecker implements UpdateChecker {
    private static final String HOST = "github.com";
    private final GitHubClient gitHubClient;
    private final LinkUpdater linkUpdater;

    @Autowired
    public GitHubUpdateChecker(GitHubClient gitHubClient, LinkUpdater linkUpdater) {
        this.gitHubClient = gitHubClient;
        this.linkUpdater = linkUpdater;
    }

    @Override
    public Optional<String> checkUpdates(Link link) {
        String[] segments = link.getUrl().getPath().split("/");
        String ownerName = segments[1];
        String repositoryName = segments[2];
        GitHubRepositoryResponse response = gitHubClient.fetchRepo(ownerName, repositoryName);

        GitHubPullRequestsResponse pullRequestsResponse = gitHubClient.fetchPullRequests(ownerName, repositoryName);

        LinkInfo linkInfo = new GithubLinkInfo(
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
