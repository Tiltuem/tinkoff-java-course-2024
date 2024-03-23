package edu.java.repository.jpa;

import edu.java.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    @Query(value = "SELECT id, url, last_update, site_id FROM links INNER JOIN user_links ON id = link_id WHERE user_id = ?1",
           nativeQuery = true)
    List<Link> findAllByUserId(Long id);
}
