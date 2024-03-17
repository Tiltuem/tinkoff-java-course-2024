package edu.java.scrapper.repository;

import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.model.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcSiteRepository;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.scrapper.IntegrationEnvironment;
import java.net.URI;
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
        linkRepository.saveUserLink(CHAT_ID, URL, siteId);
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
        linkRepository.saveUserLink(CHAT_ID, URL, siteId);
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
        linkRepository.saveUserLink(CHAT_ID, firstUrl, siteId);
        linkRepository.saveUserLink(CHAT_ID, secondUrl, siteId);
        linkRepository.saveUserLink(CHAT_ID, thirdUrl, siteId);

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
        linkRepository.saveUserLink(CHAT_ID, URL, siteId);

        assertThatThrownBy(() -> {
            linkRepository.saveUserLink(CHAT_ID, URL, siteId);
        }).isInstanceOf(LinkAlreadyTrackedException.class)
            .hasMessageContaining("Link with URL = %s already tracked.".formatted(URL));
    }
}
