package edu.java.repository.jdbc;

import edu.java.dto.LinkResponse;
import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackedException;
import edu.java.model.GithubLinkInfo;
import edu.java.model.Link;
import edu.java.model.LinkInfo;
import edu.java.model.StackoverflowLinkInfo;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private static final String ADD_LINK = "INSERT INTO links(url, last_update, site_id) VALUES (?, ?, ?)";
    private static final String ADD_USER_LINK = "INSERT INTO user_links(user_id, link_id) VALUES (?, ?)";
    private static final String ADD_SPECIFIC_LINK = "INSERT INTO %s_links(link_id) VALUES (?)";
    private static final String RE3MOVE_SPECIFIC_LINK = "DELETE FROM %s_links WHERE link_id = ?";
    private static final String REMOVE_LINK = "DELETE FROM links WHERE id = ?";
    private static final String REMOVE_USER_LINK = "DELETE FROM user_links WHERE user_id = ? AND link_id = ?";
    private static final String FIND_ALL_LINKS_BY_USER_ID =
        "SELECT id, url, site_id, last_update FROM links INNER JOIN user_links ON id = link_id WHERE user_id = ?";
    private static final String FIND_ALL_LINKS_WITH_CHECK_INTERVAL =
        "SELECT * FROM links WHERE last_check IS NULL OR EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - last_check)) > ?";
    private static final String FIND_LINK_BY_ID = "SELECT * FROM links WHERE id = ?";
    private static final String UPDATE_LAST_CHECK_LINK =
        "UPDATE links SET last_check = NOW()::timestamp, last_update = ? WHERE id = ?";
    private static final String CHAT_ID_NOT_FOUND = "ChatId = %d not found.";
    private static final String LINK_URL_NOT_FOUND = "Link with url = %s not found.";
    private static final BeanPropertyRowMapper<Link> MAPPER = new BeanPropertyRowMapper<>(Link.class);
    private final JdbcUserRepository jdbcUserRepository;
    @Autowired
    private JdbcClient jdbcClient;

    public void saveUserLink(Long chatId, URI url, Long siteId) {
        Long userId = jdbcUserRepository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.formatted(chatId))).getId();
        Optional<Link> link = findByUrl(url);

        if (link.isEmpty()) {
            jdbcClient.sql(ADD_LINK).param(url.toString()).param(OffsetDateTime.now()).param(siteId).update();
            Long linkId = findByUrl(url).get().getId();
            saveLinkToSpecificTable(url, linkId);
        }

        Long linkId = findByUrl(url).get().getId();
        if (isUserLinkExists(userId, linkId)) {
            throw new LinkAlreadyTrackedException("Link with URL = %s already tracked.".formatted(url));
        }

        jdbcClient.sql(ADD_USER_LINK).param(userId).param(linkId).update();
    }

    public LinkResponse removeUserLink(Long chatId, URI url) {
        Long linkId = findByUrl(url)
            .orElseThrow(() -> new LinkIsNotTrackedException(LINK_URL_NOT_FOUND.formatted(url))).getId();
        Long userId = jdbcUserRepository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.formatted(chatId))).getId();

        if (jdbcClient.sql(REMOVE_USER_LINK).param(userId).param(linkId).update() == 0) {
            throw new LinkIsNotTrackedException(LINK_URL_NOT_FOUND.formatted(url));
        }

        if (isLinkInUserLinksExists(linkId)) {
            jdbcClient.sql(REMOVE_LINK).param(linkId).update();
            removeLinkToSpecificTable(url, linkId);
        }

        return new LinkResponse(linkId, url);
    }

    public List<Link> findAllLinksByUserId(Long id) {
        return jdbcClient.sql(FIND_ALL_LINKS_BY_USER_ID).param(id).query(MAPPER).list();
    }

    public Optional<Link> findByUrl(URI url) {
        String find = "SELECT * FROM links WHERE url = ?";
        String urlString = url.toString();
        try {
            return Optional.of(jdbcClient.sql(find).param(urlString).query(MAPPER).single());
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public LinkInfo updateLink(LinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        jdbcClient.sql(UPDATE_LAST_CHECK_LINK).param(linkInfo.getLastUpdate()).param(link.getId()).update();
        LinkInfo oldInfo;

        if (linkInfo instanceof GithubLinkInfo) {
            String find = "SELECT last_update, pull_requests_count FROM github_links WHERE link_id = ? FOR UPDATE";
            oldInfo = jdbcClient.sql(find).param(link.getId()).query((rs, rowNum) -> {
                Integer pullRequestsCount = rs.getInt("pull_requests_count");
                return new GithubLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), pullRequestsCount);
            }).single();

            String update = "UPDATE github_links SET pull_requests_count = ? WHERE link_id = ?";
            jdbcClient.sql(update).param(((GithubLinkInfo) linkInfo).getPullRequestsCount(), link.getId());
        } else if (linkInfo instanceof StackoverflowLinkInfo) {
            String find = "SELECT last_update, answers_count FROM github_links WHERE link_id = ? FOR UPDATE";
            oldInfo = jdbcClient.sql(find).param(link.getId()).query((rs, rowNum) -> {
                Integer answersCount = rs.getInt("answers_count");
                return new StackoverflowLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), answersCount);
            }).single();

            String update = "UPDATE stackoverflow_links SET answers_count = ? WHERE link_id = ?";
            jdbcClient.sql(update).param(((StackoverflowLinkInfo) linkInfo).getAnswersCount(), link.getId());
        } else {
            throw new RuntimeException();
        }

        return oldInfo;
    }

    public List<Link> findAllLinksWithCheckInterval(Long interval) {
        return jdbcClient.sql(FIND_ALL_LINKS_WITH_CHECK_INTERVAL).param(interval).query(MAPPER).list();
    }

    private boolean isLinkInUserLinksExists(Long linkId) {
        String find = "SELECT * FROM user_links WHERE link_id = ?";
        return jdbcClient.sql(find).param(linkId).query().listOfRows().isEmpty();
    }

    private boolean isUserLinkExists(Long userId, Long linkId) {
        String find = "SELECT * FROM user_links WHERE link_id = ? AND user_id = ?";
        return !jdbcClient.sql(find).param(linkId).param(userId).query().listOfRows().isEmpty();
    }

    @SuppressWarnings("MagicNumber")
    private void saveLinkToSpecificTable(URI url, Long linkId) {
        jdbcClient.sql(ADD_SPECIFIC_LINK.formatted(url.getHost().substring(0, url.getHost().length() - 4)))
            .param(linkId).update();
    }

    @SuppressWarnings("MagicNumber")
    private void removeLinkToSpecificTable(URI url, Long linkId) {
        jdbcClient.sql(RE3MOVE_SPECIFIC_LINK.formatted(url.getHost().substring(0, url.getHost().length() - 4)))
            .param(linkId).update();
    }
}
