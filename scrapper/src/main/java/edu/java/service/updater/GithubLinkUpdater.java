package edu.java.service.updater;

import edu.java.model.info.GithubLinkInfo;
import edu.java.repository.GithubLinkRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GithubLinkUpdater<T extends GithubLinkRepository> {
    private final T repository;

    public Optional<String> update(GithubLinkInfo linkInfo) {
        GithubLinkInfo oldInfo = repository.updateLink(linkInfo);

        String response = null;
        if (linkInfo.getLastUpdate().isPresent() && oldInfo.getLastUpdate().isPresent()
            && linkInfo.getLastUpdate().get().isAfter(oldInfo.getLastUpdate()
            .get())) {
            response = "Link updated";

            if (!Objects.equals(
                linkInfo.getPullRequestsCount(),
                oldInfo.getPullRequestsCount()
            )) {
                response += ": has new pull requests";
            }
        }
        return Optional.ofNullable(response);
    }
}
