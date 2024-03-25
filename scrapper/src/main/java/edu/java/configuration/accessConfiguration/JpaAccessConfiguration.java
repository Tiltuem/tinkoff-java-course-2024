package edu.java.configuration.accessConfiguration;

import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaSiteRepository;
import edu.java.repository.jpa.JpaUserRepository;
import edu.java.service.LinkService;
import edu.java.service.SiteService;
import edu.java.service.UserService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaSiteService;
import edu.java.service.jpa.JpaUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration {
    private final JpaUserRepository jpaUserRepository;
    private final JpaSiteRepository jpaSiteRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Bean
    public UserService userService() {
        return new JpaUserService(jpaUserRepository);
    }

    @Bean
    public LinkService linkService() {
        return new JpaLinkService(jpaLinkRepository, jpaSiteRepository, jpaUserRepository);
    }

    @Bean
    public SiteService siteService() {
        return new JpaSiteService(jpaSiteRepository);
    }
}
