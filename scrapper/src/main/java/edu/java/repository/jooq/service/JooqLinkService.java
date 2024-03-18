package edu.java.repository.jooq.service;

import edu.java.dto.LinkResponse;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Link;
import edu.java.model.LinkInfo;
import edu.java.repository.jooq.repository.JooqLinkRepository;
import edu.java.repository.jooq.repository.JooqSiteRepository;
import edu.java.repository.jooq.repository.JooqUserRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository linkRepository;
    private final JooqUserRepository userRepository;
    private final JooqSiteRepository siteRepository;

    @Override
    public LinkResponse addUserLink(Long chatId, URI url) {
        LinkInfo linkInfo;


        return new LinkResponse(linkRepository.findByUrl(url).get().getId(), url);
    }

    @Override
    public LinkResponse removeUserLink(Long chatId, URI url) {
        return linkRepository.removeUserLink(chatId, url);
    }

    @Override
    public List<Link> getAllUserLinks(Long id) {
        return linkRepository.findAllLinksByUserId(id);
    }

    @Override
    public List<Link> findLinksForUpdate(Long interval) {
        return linkRepository.findAllLinksWithCheckInterval(interval);
    }

    private Long parseSite(URI url) {
        return siteRepository.findByName(url.getHost())
            .orElseThrow(
                () -> new SiteNotFoundException("The site with the url = %s not found.".formatted(url.getHost())))
            .getId();
    }
}
