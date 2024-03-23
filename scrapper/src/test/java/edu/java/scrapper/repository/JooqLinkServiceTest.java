package edu.java.scrapper.repository;

import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.model.Link;
import edu.java.repository.jooq.service.JooqLinkService;
import edu.java.repository.jooq.service.JooqSiteService;
import edu.java.repository.jooq.service.JooqUserService;
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
public class JooqLinkServiceTest extends IntegrationEnvironment {
    @Autowired
    private JooqLinkService linkService;
    @Autowired
    private JooqUserService userService;
    @Autowired
    private JooqSiteService siteService;

    private static final URI URL = URI.create("https://github.com/Tiltuem/tinkoff-java-course-2024");
    private static final Long CHAT_ID = 123L;

    @Test
    @Transactional
    @Rollback
    public void testAddLink() {
        userService.addUser(CHAT_ID);
        siteService.addSite("github.com");
        linkService.addUserLink(CHAT_ID, URL);

        Long userId = userService.getByChatId(CHAT_ID).getId();

        List<Link> allLinks = linkService.getAllUserLinks(userId);
        assertThat(allLinks.size()).isEqualTo(1);
        assertThat(allLinks.getFirst().getUrl()).isEqualTo(URL);
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveLinkByUrl() {
        userService.addUser(CHAT_ID);
        siteService.addSite("github.com");
        linkService.addUserLink(CHAT_ID, URL);
        linkService.removeUserLink(CHAT_ID, URL);
        Long userId = userService.getByChatId(CHAT_ID).getId();

        List<Link> allLinks = linkService.getAllUserLinks(userId);
        assertThat(allLinks.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAllLinksForUser() {
        URI firstUrl = URI.create("https://github.com/Tiltuem");
        URI secondUrl = URI.create("https://github.com/Tiltuem/tinkoff-java-course");
        URI thirdUrl = URI.create("https://github.com/sanyarnd");

        userService.addUser(CHAT_ID);
        siteService.addSite("github.com");
        linkService.addUserLink(CHAT_ID, firstUrl);
        linkService.addUserLink(CHAT_ID, secondUrl);
        linkService.addUserLink(CHAT_ID, thirdUrl);

        Long userId = userService.getByChatId(CHAT_ID).getId();
        List<Link> allLinks = linkService.getAllUserLinks(userId);

        assertThat(allLinks.size()).isEqualTo(3);
        assertThat(allLinks.getFirst().getUrl()).isEqualTo(firstUrl);
        assertThat(allLinks.get(1).getUrl()).isEqualTo(secondUrl);
        assertThat(allLinks.get(2).getUrl()).isEqualTo(thirdUrl);
    }

    @Test
    @Transactional
    @Rollback
    public void testLinkAlreadyTracked() {
        userService.addUser(CHAT_ID);
        siteService.addSite("github.com");
        linkService.addUserLink(CHAT_ID, URL);

        assertThatThrownBy(() -> {
            linkService.addUserLink(CHAT_ID, URL);
        }).isInstanceOf(LinkAlreadyTrackedException.class)
            .hasMessageContaining("Link with URL = %s already tracked.".formatted(URL));
    }
}
