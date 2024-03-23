package edu.java.service.jpa;

import edu.java.model.Site;
import edu.java.repository.jpa.JpaSiteRepository;
import edu.java.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaSiteService implements SiteService {
    private final JpaSiteRepository repository;

    @Override
    public void addSite(String name) {
        repository.save(Site.builder().name(name).build());
    }

    @Override
    public void removeSite(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Site> getAllSites() {
        return repository.findAll();
    }
}
