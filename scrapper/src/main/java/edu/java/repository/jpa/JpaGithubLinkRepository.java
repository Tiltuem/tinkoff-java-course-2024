package edu.java.repository.jpa;

import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.repository.GithubLinkRepository;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaGithubLinkRepository extends GithubLinkRepository {
    private final JpaLinkRepository repository;

    @Override
    @Transactional
    public GithubLinkInfo updateLink(GithubLinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        link.setLastUpdate(linkInfo.getLastUpdate().get());
        link.setLastCheck(OffsetDateTime.now());
        repository.save(link);

        GithubLinkInfo oldInfo;

        Integer pullRequestsCount = repository.findPullRequestsCountAtLink(link.getId());
        oldInfo = new GithubLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), pullRequestsCount);

        if (!Objects.equals(pullRequestsCount, linkInfo.getPullRequestsCount())) {
            repository.updateGithubLink(link.getId(), linkInfo.getPullRequestsCount());
        }

        return oldInfo;
    }
}
