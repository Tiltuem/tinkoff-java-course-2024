package edu.java.service.jpa;

import edu.java.exception.SiteAlreadyExistsException;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Site;
import edu.java.repository.jpa.JpaSiteRepository;
import edu.java.service.SiteService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaSiteService implements SiteService {
    private final JpaSiteRepository repository;

    @Override
    public void addSite(String name) {
        if (repository.findBySiteName(name).isPresent()) {
            throw new SiteAlreadyExistsException("Site with URL = %s already exists".formatted(name));
        }

        repository.save(Site.builder().siteName(name).build());
    }

    @Override
    public void removeSite(Long id) {
        if (repository.findById(id).isPresent()) {
            throw new SiteNotFoundException("Site not found.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<Site> getAllSites() {
        return repository.findAll();
    }
}
