package edu.java.service.jpa;

import edu.java.dto.LinkResponse;
import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackedException;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Link;
import edu.java.model.info.GithubLinkInfo;
import edu.java.model.info.LinkInfo;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaSiteRepository;
import edu.java.repository.jpa.JpaUserRepository;
import edu.java.service.LinkService;
import jakarta.transaction.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import static edu.java.exception.ExceptionsString.CHAT_ID_NOT_FOUND;
import static edu.java.exception.ExceptionsString.LINK_IS_ALREADY_TRACKED;
import static edu.java.exception.ExceptionsString.LINK_URL_NOT_FOUND;
import static edu.java.exception.ExceptionsString.SITE_URL_NOT_FOUND;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository repository;
    private final JpaSiteRepository siteRepository;
    private final JpaUserRepository userRepository;

    @Override
    @Transactional
    public LinkResponse addUserLink(Long chatId, URI uri) {
        Long userId = getUserIdByChatId(chatId);
        Optional<Link> link = repository.findByUrl(uri);
        Long linkId;

        if (link.isEmpty()) {
            Link newLink = Link.builder().url(uri).siteId(parseSite(uri)).build();
            repository.save(newLink);
            linkId = newLink.getId();
            addLinkToSpecificTable(uri, linkId);
        } else {
            linkId = link.get().getId();
            if (isUserLinkExists(userId, linkId)) {
                throw new LinkAlreadyTrackedException(LINK_IS_ALREADY_TRACKED.getMessage().formatted(uri));
            }
        }

        repository.saveUserLink(userId, linkId);

        return new LinkResponse(linkId, uri);
    }

    @Override
    @Transactional
    public LinkResponse removeUserLink(Long chatId, URI uri) {
        Long linkId = repository.findByUrl(uri)
            .orElseThrow(() -> new LinkIsNotTrackedException(LINK_URL_NOT_FOUND.getMessage().formatted(uri))).getId();
        Long userId = getUserIdByChatId(chatId);

        if (isUserLinkExists(userId, linkId)) {
            repository.deleteUserLink(userId, linkId);
        } else {
            throw new LinkIsNotTrackedException(LINK_URL_NOT_FOUND.getMessage().formatted(uri));
        }

        if (!isLinkInUserLinksExists(linkId)) {
            repository.deleteById(linkId);
        }

        return new LinkResponse(linkId, uri);
    }

    @Override
    public List<Link> getAllUserLinks(Long id) {
        return repository.findAllByUserId(id);
    }

    @Override
    public List<Link> findLinksForUpdate(Long interval) {
        return repository.findAllLinksWithCheckInterval(interval);
    }

    @Override
    @Transactional
    public LinkInfo updateLink(LinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        link.setLastUpdate(linkInfo.getLastUpdate().get());
        link.setLastCheck(OffsetDateTime.now());
        repository.save(link);

        LinkInfo oldInfo;

        if (linkInfo instanceof GithubLinkInfo) {
            Integer pullRequestsCount = repository.findPullRequestsCountAtLink(link.getId());
            oldInfo = new GithubLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), pullRequestsCount);

            repository.updateGithubLink(link.getId(), pullRequestsCount);
        } else if (linkInfo instanceof StackoverflowLinkInfo) {
            Integer answersCount = repository.findAnswersCountAtLink(link.getId());
            oldInfo = new StackoverflowLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), answersCount);

            repository.updateStackOverFlowLink(link.getId(), answersCount);
        } else {
            throw new RuntimeException();
        }

        return oldInfo;

    }

    private Long parseSite(URI url) {
        return siteRepository.findBySiteName(url.getHost())
            .orElseThrow(
                () -> new SiteNotFoundException(SITE_URL_NOT_FOUND.getMessage().formatted(url.getHost())))
            .getId();
    }

    private Long getUserIdByChatId(Long chatId) {
        return userRepository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.getMessage().formatted(chatId))).getId();
    }

    private void addLinkToSpecificTable(URI uri, Long linkId) {
        switch (uri.getHost().toLowerCase()) {
            case "github.com" -> repository.saveGithubLink(linkId);
            case "stackoverflow.com" -> repository.saveStackOverFlowLink(linkId);
            default -> throw new IllegalArgumentException("Invalid link type: " + uri.getHost());
        }
    }

    private boolean isUserLinkExists(Long userId, Long linkId) {
        return repository.findUserLink(userId, linkId) == 1;
    }

    private boolean isLinkInUserLinksExists(Long linkId) {
        return repository.countUserLinksById(linkId) != 0;
    }
}
