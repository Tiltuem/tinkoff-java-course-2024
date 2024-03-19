package edu.java.repository.jooq.service;

import edu.java.model.Site;
import edu.java.repository.jooq.repository.JooqSiteRepository;
import edu.java.service.SiteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqSiteService implements SiteService {
    private final JooqSiteRepository jooqSiteRepository;

    @Override
    public void addSite(String name) {
        jooqSiteRepository.save(name);
    }

    @Override
    public void removeSite(Long id) {
        jooqSiteRepository.remove(id);
    }

    @Override
    public List<Site> getAllSites() {
        return jooqSiteRepository.findAll();
    }
}
