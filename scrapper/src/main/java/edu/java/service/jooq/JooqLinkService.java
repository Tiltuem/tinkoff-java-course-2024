package edu.java.service.jooq;

import edu.java.dto.LinkResponse;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.GithubLinkInfo;
import edu.java.model.Link;
import edu.java.model.LinkInfo;
import edu.java.model.StackoverflowLinkInfo;
import edu.java.repository.jooq.repository.JooqLinkRepository;
import edu.java.repository.jooq.repository.JooqSiteRepository;
import edu.java.repository.jooq.repository.JooqUserRepository;
import edu.java.service.LinkService;
import edu.java.util.updateChecker.UpdateChecker;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository linkRepository;
    private final JooqUserRepository userRepository;
    private final JooqSiteRepository siteRepository;
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
        LinkInfo linkInfo;
        if (link.getSiteId() == 1) {
            linkInfo = new GithubLinkInfo(link, Optional.of(OffsetDateTime.now()), 0);
        } else {
            linkInfo = new StackoverflowLinkInfo(link, Optional.of(OffsetDateTime.now()), 0);
        }

        linkRepository.saveUserLink(chatId, linkInfo);

        return new LinkResponse(linkRepository.findByUrl(url).get().getId(), url);
    }

    @Override
    public LinkResponse removeUserLink(Long chatId, URI uri) {
        return linkRepository.removeUserLink(chatId, uri);
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
