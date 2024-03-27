package edu.java.repository.jooq.repository;

import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.repository.GithubLinkRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jooq.Tables.GITHUB_LINKS;
import static edu.java.repository.jooq.Tables.LINKS;

@RequiredArgsConstructor
public class JooqGithubLinkRepository extends GithubLinkRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional
    public GithubLinkInfo updateLink(GithubLinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        dslContext.update(LINKS)
            .set(LINKS.LAST_CHECK, OffsetDateTime.now())
            .set(LINKS.LAST_UPDATE, linkInfo.getLastUpdate().get())
            .where(LINKS.ID.eq(link.getId()))
            .execute();
        GithubLinkInfo oldInfo;

        oldInfo = dslContext.select(GITHUB_LINKS.PULL_REQUESTS_COUNT).from(LINKS)
            .innerJoin(GITHUB_LINKS).on(GITHUB_LINKS.LINK_ID.eq(LINKS.ID))
            .where(LINKS.ID.eq(link.getId()))
            .fetchOne()
            .map(row -> new GithubLinkInfo(
                link,
                Optional.ofNullable(link.getLastUpdate()),
                row.get(GITHUB_LINKS.PULL_REQUESTS_COUNT)
            ));

        if (!Objects.equals(oldInfo.getPullRequestsCount(), linkInfo.getPullRequestsCount())) {
            dslContext.update(GITHUB_LINKS)
                .set(GITHUB_LINKS.PULL_REQUESTS_COUNT, linkInfo.getPullRequestsCount())
                .where(GITHUB_LINKS.LINK_ID.eq(link.getId())).execute();
        }

        return oldInfo;
    }
}
