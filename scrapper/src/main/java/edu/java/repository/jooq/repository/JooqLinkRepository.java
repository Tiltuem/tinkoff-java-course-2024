package edu.java.repository.jooq.repository;

import edu.java.dto.LinkResponse;
import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackedException;
import edu.java.model.GithubLinkInfo;
import edu.java.model.Link;
import edu.java.model.LinkInfo;
import edu.java.model.StackoverflowLinkInfo;
import edu.java.model.User;
import edu.java.repository.jooq.tables.records.LinksRecord;
import edu.java.repository.jooq.tables.records.UsersRecord;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jooq.Tables.GITHUB_LINKS;
import static edu.java.repository.jooq.Tables.LINKS;
import static edu.java.repository.jooq.Tables.STACKOVERFLOW_LINKS;
import static edu.java.repository.jooq.Tables.USERS;
import static edu.java.repository.jooq.Tables.USER_LINKS;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext dslContext;

    @Transactional
    public void saveUserLink(Long chatId, LinkInfo linkInfo) {
        LinksRecord linksRecord = addLinkRecord(linkInfo.getLink().getUrl());

        switch (linkInfo.getLink().getUrl().getHost().toLowerCase()) {
            case "github" -> addGithubLinkRecord(linksRecord.getId(), ((GithubLinkInfo) linkInfo));
            case "stackoverflow" -> addStackoverflowLinkRecord(linksRecord.getId(), ((StackoverflowLinkInfo) linkInfo));
            default -> throw new IllegalArgumentException("Invalid link type: " + linkInfo.getLink().getUrl().getHost());
        }
        Long userId = dslContext
            .selectFrom(USERS)
            .where(USERS.CHAT_ID.eq(chatId))
            .fetchOptional()
            .map(rec -> new User(rec.getId(), rec.getChatId()))
            .orElseThrow(() -> new ChatIdNotFoundException("ChatId = %d not found.".formatted(chatId))).getId();
        addUserLink(userId, linksRecord.getId());
    }

    @Transactional
    public LinkResponse removeUserLink(Long chatId, URI url) {
        UsersRecord usersRecord = dslContext.selectFrom(USERS).where(USERS.CHAT_ID.eq(chatId)).fetchOne();

        if (usersRecord == null) {
            throw new ChatIdNotFoundException("ChatId = %d not found.".formatted(chatId));
        }
        Long userId = usersRecord.getId();

        LinksRecord linksRecord = dslContext.selectFrom(LINKS)
            .where(LINKS.URL.eq(url.toString()))
            .fetchOne();

        if (linksRecord == null) {
            throw new LinkIsNotTrackedException("Link with URL = %s not found".formatted(url));
        }
        Long linkId = linksRecord.getId();

        int rowsAffected = dslContext.deleteFrom(USER_LINKS)
            .where(USER_LINKS.USER_ID.eq(userId)
                .and(USER_LINKS.LINK_ID.eq(linkId)))
            .execute();

        if (rowsAffected == 0) {
            throw new LinkIsNotTrackedException("Link with URL = %s not found".formatted(url));
        }

        clearDBFromUntrackedLink(linkId);

        return new LinkResponse(linkId, url);
    }

    public List<Link> findAllLinksByUserId(Long userId) {
        return dslContext.select(LINKS.ID, LINKS.URL)
            .from(LINKS)
            .join(USER_LINKS).on(USER_LINKS.LINK_ID.eq(LINKS.ID))
            .where(USER_LINKS.USER_ID.eq(userId))
            .fetch()
            .map(rec -> new Link(
                rec.get(LINKS.ID),
                URI.create(rec.get(LINKS.URL)),
                rec.get(LINKS.LAST_UPDATE),
                rec.get(LINKS.SITE_ID)
            ));
    }

    public Optional<Link> findByUrl(URI url) {
        return dslContext.selectFrom(LINKS).where(LINKS.URL.eq(url.toString())).fetchOptional()
            .map(rec -> new Link(rec.getId(), URI.create(rec.getUrl()), rec.getLastUpdate(), rec.getSiteId()));
    }

    public List<Link> findAllLinksWithCheckInterval(Long interval) {
        OffsetDateTime threshold = OffsetDateTime.now().minusSeconds(interval);

        Result<Record> result = dslContext.select()
            .from(LINKS)
            .where(LINKS.LAST_CHECK.isNull().or(LINKS.LAST_CHECK.lessOrEqual(threshold)))
            .fetch();

        return result.map(rec -> new Link(
            rec.get(LINKS.ID),
            URI.create(rec.get(LINKS.URL)),
            rec.get(LINKS.LAST_UPDATE),
            rec.get(LINKS.SITE_ID)
        ));
    }

    private LinksRecord addLinkRecord(URI url) {
        return dslContext.insertInto(LINKS)
            .set(LINKS.URL, url.toString())
            .set(LINKS.LAST_UPDATE, OffsetDateTime.now())
            .set(LINKS.LAST_CHECK, OffsetDateTime.now())
            .onDuplicateKeyIgnore()
            .returning(LINKS.ID)
            .fetchOne();
    }

    private void addGithubLinkRecord(Long linkId, GithubLinkInfo linkInfo) {
        dslContext.insertInto(GITHUB_LINKS)
            .set(GITHUB_LINKS.LINK_ID, linkId)
            .set(GITHUB_LINKS.PULL_REQUESTS_COUNT, linkInfo.getPullRequestsCount())
            .onDuplicateKeyIgnore()
            .execute();
    }

    private void addStackoverflowLinkRecord(Long linkId, StackoverflowLinkInfo linkInfo) {
        dslContext.insertInto(STACKOVERFLOW_LINKS)
            .set(STACKOVERFLOW_LINKS.LINK_ID, linkId)
            .set(STACKOVERFLOW_LINKS.ANSWERS_COUNT, linkInfo.getAnswersCount())
            .onDuplicateKeyIgnore()
            .execute();
    }

    private void addUserLink(Long userId, Long linkId) {
        int rowsAffected = dslContext.insertInto(USER_LINKS)
            .set(USER_LINKS.USER_ID, userId)
            .set(USER_LINKS.LINK_ID, linkId)
            .onDuplicateKeyIgnore()
            .execute();
        if (rowsAffected == 0) {
            throw new LinkAlreadyTrackedException("You already tracking this link");
        }
    }

    private boolean isLinkTracked(Long linkId) {
        return dslContext.fetchExists(USER_LINKS, USER_LINKS.LINK_ID.eq(linkId));
    }

    private void clearDBFromUntrackedLink(Long linkId) {
        if (!isLinkTracked(linkId)) {
            dslContext.deleteFrom(GITHUB_LINKS)
                .where(LINKS.ID.eq(linkId))
                .execute();
            dslContext.deleteFrom(STACKOVERFLOW_LINKS)
                .where(LINKS.ID.eq(linkId))
                .execute();
            dslContext.deleteFrom(LINKS)
                .where(LINKS.ID.eq(linkId))
                .execute();
        }
    }
}
