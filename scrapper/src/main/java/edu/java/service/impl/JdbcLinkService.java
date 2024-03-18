package edu.java.service.impl;

import edu.java.dto.LinkResponse;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcSiteRepository;
import edu.java.service.LinkService;
import edu.java.util.updateChecker.UpdateChecker;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository repository;
    private final JdbcSiteRepository jdbcSiteRepository;
    private final List<UpdateChecker> updateCheckerList;

    @Override
    public LinkResponse addUserLink(Long chatId, URI url) {
        Link link = new Link(null, url, OffsetDateTime.now(), parseSite(url));
        for (UpdateChecker checker : updateCheckerList) {
            if (checker.isAppropriateLink(link)) {
                try {
                    checker.checkUpdates(link);
                } catch (RuntimeException e) {
                    throw new RuntimeException("Incorrect link, please try again");
                }
            }
        }

        repository.saveUserLink(chatId, link);
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
                () -> new SiteNotFoundException("The site with the url = %s not found.".formatted(url.getHost())))
            .getId();
    }
}
