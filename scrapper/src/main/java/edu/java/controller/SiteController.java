package edu.java.controller;

import edu.java.model.Site;
import edu.java.service.SiteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sites")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;

    @GetMapping("/get-all")
    public List<Site> getAllSites() {
       return siteService.getAllSites();
    }

    @PostMapping("/add/{name}")
    public void addSite(@PathVariable String name) {
        siteService.addSite(name);
    }

    @DeleteMapping("/delete/{id}")
    public void removeSite(@PathVariable Long id) {
        siteService.removeSite(id);
    }
}
