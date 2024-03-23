package edu.java.repository.jpa;

import edu.java.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSiteRepository extends JpaRepository<Site, Long> {
}
