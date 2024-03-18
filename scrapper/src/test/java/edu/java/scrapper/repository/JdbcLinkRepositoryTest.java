package edu.java.scrapper.repository;

import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.model.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcSiteRepository;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.scrapper.IntegrationEnvironment;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcUserRepository userRepository;
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcSiteRepository siteRepository;
    private static final URI URL = URI.create("https://github.com/Tiltuem/tinkoff-java-course-2024");
    private static final Long CHAT_ID = 123L;

    @Test
    @Transactional
    @Rollback
    public void testAddLink() {
        userRepository.save(CHAT_ID);
        siteRepository.save("github.com");
        Long siteId = siteRepository.findAll().getFirst().getId();
        Link link = new Link(null, URL, OffsetDateTime.now(), siteId);
        linkRepository.saveUserLink(CHAT_ID, link);
        Long userId = userRepository.findByChatId(CHAT_ID).get().getId();

        List<Link> allLinks = linkRepository.findAllLinksByUserId(userId);
        assertThat(allLinks.size()).isEqualTo(1);
        assertThat(allLinks.getFirst().getUrl()).isEqualTo(URL);
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveLinkByUrl() {
        userRepository.save(CHAT_ID);
        siteRepository.save("github.com");
        Long siteId = siteRepository.findAll().getFirst().getId();
        Link link = new Link(null, URL, OffsetDateTime.now(), siteId);
        linkRepository.saveUserLink(CHAT_ID, link);
        linkRepository.removeUserLink(CHAT_ID, URL);
        Long userId = userRepository.findByChatId(CHAT_ID).get().getId();

        List<Link> allLinks = linkRepository.findAllLinksByUserId(userId);
        assertThat(allLinks.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAllLinksForUser() {
        URI firstUrl = URI.create("https://github.com/Tiltuem");
        URI secondUrl = URI.create("https://github.com/Tiltuem/tinkoff-java-course");
        URI thirdUrl = URI.create("https://github.com/sanyarnd");

        userRepository.save(CHAT_ID);
        siteRepository.save("github.com");
        Long siteId = siteRepository.findAll().getFirst().getId();
        Link link1 = new Link(null, firstUrl, OffsetDateTime.now(), siteId);
        Link link2 = new Link(null, secondUrl, OffsetDateTime.now(), siteId);
        Link link3 = new Link(null, thirdUrl, OffsetDateTime.now(), siteId);

        linkRepository.saveUserLink(CHAT_ID, link1);
        linkRepository.saveUserLink(CHAT_ID, link2);
        linkRepository.saveUserLink(CHAT_ID, link3);

        Long userId = userRepository.findByChatId(CHAT_ID).get().getId();
        List<Link> allLinks = linkRepository.findAllLinksByUserId(userId);

        assertThat(allLinks.size()).isEqualTo(3);
        assertThat(allLinks.getFirst().getUrl()).isEqualTo(firstUrl);
        assertThat(allLinks.get(1).getUrl()).isEqualTo(secondUrl);
        assertThat(allLinks.get(2).getUrl()).isEqualTo(thirdUrl);
    }

    @Test
    @Transactional
    @Rollback
    public void testLinkAlreadyTracked() {
        userRepository.save(CHAT_ID);
        siteRepository.save("github.com");
        Long siteId = siteRepository.findAll().getFirst().getId();
        Link link = new Link(null, URL, OffsetDateTime.now(), siteId);
        linkRepository.saveUserLink(CHAT_ID, link);

        assertThatThrownBy(() -> {
            linkRepository.saveUserLink(CHAT_ID, link);
        }).isInstanceOf(LinkAlreadyTrackedException.class)
            .hasMessageContaining("Link with URL = %s already tracked.".formatted(URL));
    }
}
