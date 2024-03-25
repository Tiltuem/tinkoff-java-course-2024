package edu.java.service.updater;

import edu.java.model.info.GithubLinkInfo;
import edu.java.model.info.LinkInfo;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final LinkService linkService;

    @Override
    public Optional<String> update(LinkInfo linkInfo) {
        LinkInfo oldInfo = linkService.updateLink(linkInfo);

        String response = null;
        if (linkInfo.getLastUpdate().isPresent() && oldInfo.getLastUpdate().isPresent()
            && linkInfo.getLastUpdate().get().isAfter(oldInfo.getLastUpdate()
                .get())) {
            response = "Link updated";

            if (linkInfo instanceof GithubLinkInfo && oldInfo instanceof GithubLinkInfo) {
            boolean hasNewPullRequests = !Objects.equals(
                    ((GithubLinkInfo) linkInfo).getPullRequestsCount(),
                    ((GithubLinkInfo) oldInfo).getPullRequestsCount()
                );
                if (hasNewPullRequests) {
                    response += ": has new pull requests";
                }
            } else if (linkInfo instanceof StackoverflowLinkInfo && oldInfo instanceof StackoverflowLinkInfo) {
                boolean hasNewAnswers = !Objects.equals(
                    ((StackoverflowLinkInfo) linkInfo).getAnswersCount(),
                    ((StackoverflowLinkInfo) oldInfo).getAnswersCount()
                );
                if (hasNewAnswers) {
                    response += ": has new answers";
                }
            }
        }

        return Optional.ofNullable(response);
    }
}
