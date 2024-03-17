package edu.java.service.impl;

import edu.java.model.Site;
import edu.java.repository.jdbc.JdbcSiteRepository;
import edu.java.service.SiteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcSiteService implements SiteService {
    private final JdbcSiteRepository jdbcSiteRepository;

    @Override
    public void addSite(String name) {
        jdbcSiteRepository.save(name);
    }

    @Override
    public void removeSite(Long id) {
        jdbcSiteRepository.remove(id);
    }

    @Override
    public List<Site> getAllSites() {
        return jdbcSiteRepository.findAll();
    }
}
