package edu.java.repository.jpa;

import edu.java.model.Site;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findBySiteName(String name);
}
