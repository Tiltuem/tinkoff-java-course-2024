package edu.java.repository.jooq.repository;

import edu.java.model.Link;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.repository.StackOverFlowLinkRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jooq.Tables.LINKS;
import static edu.java.repository.jooq.Tables.STACKOVERFLOW_LINKS;

@RequiredArgsConstructor
public class JooqStackOverFlowLinkRepository extends StackOverFlowLinkRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional
    public StackoverflowLinkInfo updateLink(StackoverflowLinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        dslContext.update(LINKS)
            .set(LINKS.LAST_CHECK, OffsetDateTime.now())
            .set(LINKS.LAST_UPDATE, linkInfo.getLastUpdate().get())
            .where(LINKS.ID.eq(link.getId()))
            .execute();
        StackoverflowLinkInfo oldInfo;

        oldInfo = dslContext.select(STACKOVERFLOW_LINKS.ANSWERS_COUNT).from(LINKS)
            .innerJoin(STACKOVERFLOW_LINKS).on(STACKOVERFLOW_LINKS.LINK_ID.eq(LINKS.ID))
            .where(LINKS.ID.eq(link.getId()))
            .fetchOne()
            .map(row -> new StackoverflowLinkInfo(
                link,
                Optional.ofNullable(link.getLastUpdate()),
                row.get(STACKOVERFLOW_LINKS.ANSWERS_COUNT)
            ));

        if (!Objects.equals(oldInfo.getAnswersCount(), linkInfo.getAnswersCount())) {
            dslContext.update(STACKOVERFLOW_LINKS)
                .set(STACKOVERFLOW_LINKS.ANSWERS_COUNT, linkInfo.getAnswersCount())
                .where(STACKOVERFLOW_LINKS.LINK_ID.eq(link.getId())).execute();
        }

        return oldInfo;
    }
}
