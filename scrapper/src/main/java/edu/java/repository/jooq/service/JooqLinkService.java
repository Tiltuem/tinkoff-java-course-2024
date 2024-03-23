package edu.java.repository.jooq.service;

import edu.java.dto.LinkResponse;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.GithubLinkInfo;
import edu.java.model.Link;
import edu.java.model.LinkInfo;
import edu.java.model.StackoverflowLinkInfo;
import edu.java.repository.jooq.repository.JooqLinkRepository;
import edu.java.repository.jooq.repository.JooqSiteRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository linkRepository;
    private final JooqSiteRepository siteRepository;

    @Override
    public LinkResponse addUserLink(Long chatId, URI url) {
        Link link = new Link(null, url, OffsetDateTime.now(), parseSite(url));
        LinkInfo linkInfo;
        if (Objects.equals(link.getSiteId(), siteRepository.findByName("github.com").get().getId())) {
            linkInfo = new GithubLinkInfo(link, Optional.of(OffsetDateTime.now()), 0);
        } else if (Objects.equals(link.getSiteId(), siteRepository.findByName("stackOverFlow.com").get().getId())) {
            linkInfo = new StackoverflowLinkInfo(link, Optional.of(OffsetDateTime.now()), 0);
        } else {
            throw new SiteNotFoundException("Site not found.");
        }

        linkRepository.saveUserLink(chatId, linkInfo, parseSite(url));

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
