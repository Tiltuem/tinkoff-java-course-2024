package edu.java.configuration.accessConfiguration;

import edu.java.repository.jdbc.JdbcGithubLinkRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcSiteRepository;
import edu.java.repository.jdbc.JdbcStackOverFlowLinkRepository;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.service.LinkService;
import edu.java.service.SiteService;
import edu.java.service.UserService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcSiteService;
import edu.java.service.jdbc.JdbcUserService;
import edu.java.service.updater.GithubLinkUpdater;
import edu.java.service.updater.StackOverFlowLinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {
    private final JdbcClient jdbcClient;

    @Bean
    public JdbcUserRepository userRepository() {
        return new JdbcUserRepository(jdbcClient);
    }

    @Bean
    public JdbcLinkRepository linkRepository() {
        return new JdbcLinkRepository(userRepository(), jdbcClient);
    }

    @Bean
    public JdbcSiteRepository siteRepository() {
        return new JdbcSiteRepository(jdbcClient);
    }

    @Bean
    public JdbcStackOverFlowLinkRepository stackOverFlowLinkRepository() {
        return new JdbcStackOverFlowLinkRepository(jdbcClient);
    }

    @Bean
    public JdbcGithubLinkRepository githubLinkRepository() {
        return new JdbcGithubLinkRepository(jdbcClient);
    }

    @Bean
    public UserService userService() {
        return new JdbcUserService(userRepository());
    }

    @Bean
    public LinkService linkService() {
        return new JdbcLinkService(linkRepository(), siteRepository());
    }

    @Bean
    public SiteService siteService() {
        return new JdbcSiteService(siteRepository());
    }

    @Bean
    public GithubLinkUpdater<JdbcGithubLinkRepository> githubLinkUpdater() {
        return new GithubLinkUpdater<>(githubLinkRepository());
    }

    @Bean
    public StackOverFlowLinkUpdater<JdbcStackOverFlowLinkRepository> stackOverFlowLinkUpdater() {
        return new StackOverFlowLinkUpdater<>(stackOverFlowLinkRepository());
    }
}
