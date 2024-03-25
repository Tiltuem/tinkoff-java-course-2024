package edu.java.configuration.accessConfiguration;

import edu.java.repository.jooq.repository.JooqLinkRepository;
import edu.java.repository.jooq.repository.JooqSiteRepository;
import edu.java.repository.jooq.repository.JooqUserRepository;
import edu.java.service.LinkService;
import edu.java.service.SiteService;
import edu.java.service.UserService;
import edu.java.service.jooq.JooqLinkService;
import edu.java.service.jooq.JooqSiteService;
import edu.java.service.jooq.JooqUserService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {
    private final DSLContext dslContext;

    @Bean
    public JooqUserRepository userRepository() {
        return new JooqUserRepository(dslContext);
    }

    @Bean
    public JooqLinkRepository linkRepository() {
        return new JooqLinkRepository(dslContext, userRepository());
    }

    @Bean
    public JooqSiteRepository siteRepository() {
        return new JooqSiteRepository(dslContext);
    }

    @Bean
    public UserService userService() {
        return new JooqUserService(userRepository());
    }

    @Bean
    public LinkService linkService() {
        return new JooqLinkService(linkRepository(), siteRepository());
    }

    @Bean
    public SiteService siteService() {
        return new JooqSiteService(siteRepository());
    }
}
