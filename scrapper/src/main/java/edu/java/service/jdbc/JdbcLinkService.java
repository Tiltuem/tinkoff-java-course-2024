package edu.java.service.jdbc;

import edu.java.dto.LinkResponse;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcSiteRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import static edu.java.exception.ExceptionsString.SITE_URL_NOT_FOUND;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository repository;
    private final JdbcSiteRepository jdbcSiteRepository;

    @Override
    public LinkResponse addUserLink(Long chatId, URI url) {
        repository.saveUserLink(chatId, url, parseSite(url));
        return new LinkResponse(repository.findByUrl(url).get().getId(), url);
    }

    @Override
    public LinkResponse removeUserLink(Long chatId, URI url) {
        return repository.removeUserLink(chatId, url);
    }

    @Override
    public List<Link> getAllUserLinks(Long id) {
        return repository.findAllLinksByUserId(id);
    }

    @Override
    public List<Link> findLinksForUpdate(Long interval) {
        return repository.findAllLinksWithCheckInterval(interval);
    }

    private Long parseSite(URI url) {
        return jdbcSiteRepository.findByName(url.getHost())
            .orElseThrow(
                () -> new SiteNotFoundException(SITE_URL_NOT_FOUND.getMessage().formatted(url.getHost())))
            .getId();
    }
}
