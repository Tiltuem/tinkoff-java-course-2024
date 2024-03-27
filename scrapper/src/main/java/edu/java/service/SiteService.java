package edu.java.service;

import edu.java.model.Site;
import java.util.List;

public interface SiteService {
    void addSite(String name);

    void removeSite(Long id);

    List<Site> getAllSites();

}
