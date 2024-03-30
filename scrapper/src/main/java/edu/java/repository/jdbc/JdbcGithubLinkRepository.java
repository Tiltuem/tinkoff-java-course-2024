package edu.java.repository.jdbc;

import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.repository.GithubLinkRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JdbcGithubLinkRepository extends GithubLinkRepository {
    private static final String UPDATE_LAST_CHECK_LINK =
        "UPDATE links SET last_check = NOW()::timestamp, last_update = ? WHERE id = ?";
    private final JdbcClient jdbcClient;

    @Override
    @Transactional
    public GithubLinkInfo updateLink(GithubLinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        jdbcClient.sql(UPDATE_LAST_CHECK_LINK).param(linkInfo.getLastUpdate().get()).param(link.getId()).update();
        GithubLinkInfo oldInfo;

        String find =
            "SELECT pull_requests_count FROM links "
                + "INNER JOIN github_links ON id = link_id WHERE link_id = ? FOR UPDATE";
        oldInfo = jdbcClient.sql(find).param(link.getId()).query((rs, rowNum) -> {
            Integer pullRequestsCount = rs.getInt("pull_requests_count");
            return new GithubLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), pullRequestsCount);
        }).single();

        if (!Objects.equals(oldInfo.getPullRequestsCount(), linkInfo.getPullRequestsCount())) {
            String update = "UPDATE github_links SET pull_requests_count = ? WHERE link_id = ?";
            jdbcClient.sql(update).param(linkInfo.getPullRequestsCount(), link.getId());
        }
        return oldInfo;
    }
}
