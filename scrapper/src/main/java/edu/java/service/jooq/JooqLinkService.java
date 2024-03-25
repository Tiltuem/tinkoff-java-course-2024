package edu.java.service.jooq;

import edu.java.dto.LinkResponse;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.model.info.LinkInfo;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.repository.jooq.repository.JooqLinkRepository;
import edu.java.repository.jooq.repository.JooqSiteRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import static edu.java.exception.ExceptionsString.SITE_URL_NOT_FOUND;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository linkRepository;
    private final JooqSiteRepository siteRepository;

    @Override
    public LinkResponse addUserLink(Long chatId, URI url) {
        Link link = Link.builder().url(url).lastCheck(OffsetDateTime.now()).siteId(parseSite(url)).build();

        LinkInfo linkInfo;
        if (Objects.equals(link.getSiteId(), siteRepository.findByName("github.com").get().getId())) {
            linkInfo = new GithubLinkInfo(link, Optional.of(OffsetDateTime.now()), 0);
        } else if (Objects.equals(link.getSiteId(), siteRepository.findByName("stackOverFlow.com").get().getId())) {
            linkInfo = new StackoverflowLinkInfo(link, Optional.of(OffsetDateTime.now()), 0);
        } else {
            throw new SiteNotFoundException(SITE_URL_NOT_FOUND.getMessage().formatted(url.getHost()));
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

    @Override
    public LinkInfo updateLink(LinkInfo linkInfo) {
        return linkRepository.updateLink(linkInfo);
    }

    private Long parseSite(URI url) {
        return siteRepository.findByName(url.getHost())
            .orElseThrow(
                () -> new SiteNotFoundException(SITE_URL_NOT_FOUND.getMessage().formatted(url.getHost())))
            .getId();
    }
}
