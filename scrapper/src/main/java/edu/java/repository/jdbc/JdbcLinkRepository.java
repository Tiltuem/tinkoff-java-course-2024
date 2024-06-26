package edu.java.repository.jdbc;

import edu.java.dto.LinkResponse;
import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackedException;
import edu.java.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import static edu.java.exception.ExceptionsString.CHAT_ID_NOT_FOUND;
import static edu.java.exception.ExceptionsString.LINK_IS_ALREADY_TRACKED;
import static edu.java.exception.ExceptionsString.LINK_URL_NOT_FOUND;


@RequiredArgsConstructor
public class JdbcLinkRepository {
    private static final String ADD_LINK = "INSERT INTO links(url, last_update, site_id) VALUES (?, ?, ?)";
    private static final String ADD_USER_LINK = "INSERT INTO user_links(user_id, link_id) VALUES (?, ?)";
    private static final String ADD_SPECIFIC_LINK = "INSERT INTO %s_links(link_id) VALUES (?)";
    private static final String REMOVE_LINK = "DELETE FROM links WHERE id = ?";
    private static final String REMOVE_USER_LINK = "DELETE FROM user_links WHERE user_id = ? AND link_id = ?";
    private static final String FIND_ALL_LINKS_BY_USER_ID =
        "SELECT id, url, site_id, last_update FROM links INNER JOIN user_links ON id = link_id WHERE user_id = ?";
    private static final String FIND_ALL_LINKS_WITH_CHECK_INTERVAL =
        "SELECT * FROM links WHERE last_check IS NULL OR EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - last_check)) > ?";
    private static final String UPDATE_LAST_CHECK_LINK =
        "UPDATE links SET last_check = NOW()::timestamp, last_update = ? WHERE id = ?";
    private static final BeanPropertyRowMapper<Link> MAPPER = new BeanPropertyRowMapper<>(Link.class);
    private final JdbcUserRepository jdbcUserRepository;
    private final JdbcClient jdbcClient;

    public void saveUserLink(Long chatId, URI url, Long siteId) {
        Long userId = jdbcUserRepository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.getMessage().formatted(chatId))).getId();
        Optional<Link> link = findByUrl(url);

        if (link.isEmpty()) {
            jdbcClient.sql(ADD_LINK).param(url.toString()).param(OffsetDateTime.now()).param(siteId).update();
            Long linkId = findByUrl(url).get().getId();
            saveLinkToSpecificTable(url, linkId);
        }

        Long linkId = findByUrl(url).get().getId();
        if (isUserLinkExists(userId, linkId)) {
            throw new LinkAlreadyTrackedException(LINK_IS_ALREADY_TRACKED.getMessage().formatted(url));
        }

        jdbcClient.sql(ADD_USER_LINK).param(userId).param(linkId).update();
    }

    public LinkResponse removeUserLink(Long chatId, URI url) {
        Long linkId = findByUrl(url)
            .orElseThrow(() -> new LinkIsNotTrackedException(LINK_URL_NOT_FOUND.getMessage().formatted(url))).getId();
        Long userId = jdbcUserRepository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.getMessage().formatted(chatId))).getId();

        if (jdbcClient.sql(REMOVE_USER_LINK).param(userId).param(linkId).update() == 0) {
            throw new LinkIsNotTrackedException(LINK_URL_NOT_FOUND.getMessage().formatted(url));
        }

        if (isLinkInUserLinksExists(linkId)) {
            jdbcClient.sql(REMOVE_LINK).param(linkId).update();
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
}
