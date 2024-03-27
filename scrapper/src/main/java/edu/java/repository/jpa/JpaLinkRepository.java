package edu.java.repository.jpa;

import edu.java.model.Link;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    @Query(value = "SELECT id, url, last_update, site_id, last_check FROM links "
        + "INNER JOIN user_links ON id = link_id WHERE user_id = ?1",
           nativeQuery = true)
    List<Link> findAllByUserId(Long id);

    Optional<Link> findByUrl(URI uri);

    @Query(value = "SELECT count(*) FROM user_links WHERE user_id = ?1 AND link_id = ?2",
           nativeQuery = true)
    Long findUserLink(Long userId, Long linkId);

    @Query(value = "SELECT count(*) FROM user_links WHERE link_id = ?1",
           nativeQuery = true)
    Long countUserLinksById(Long linkId);

    @Query(value = "SELECT * FROM links WHERE last_check IS NULL OR "
        + "EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - last_check)) > ?1",
           nativeQuery = true)
    List<Link> findAllLinksWithCheckInterval(Long interval);

    @Query(value = "SELECT pull_requests_count FROM links "
        + "INNER JOIN github_links ON id = link_id WHERE link_id = ?1 FOR UPDATE",
           nativeQuery = true)
    Integer findPullRequestsCountAtLink(Long id);

    @Query(value = "SELECT answers_count FROM links "
        + "INNER JOIN stackoverflow_links ON id = link_id WHERE link_id = ?1 FOR UPDATE",
           nativeQuery = true)
    Integer findAnswersCountAtLink(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO github_links(link_id) VALUES (?1)",
           nativeQuery = true)
    void saveGithubLink(Long linkId);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO stackoverflow_links(link_id) VALUES (?1)",
           nativeQuery = true)
    void saveStackOverFlowLink(Long linkId);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO user_links(user_id, link_id) VALUES (?1, ?2)",
           nativeQuery = true)
    void saveUserLink(Long userId, Long linkId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM user_links WHERE user_id = ?1 AND link_id = ?2",
           nativeQuery = true)
    void deleteUserLink(Long userId, Long linkId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE github_links SET pull_requests_count = ?2 WHERE link_id = ?1",
           nativeQuery = true)
    void updateGithubLink(Long linkId, Integer pullRequestsCount);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE stackoverflow_links SET answers_count = ?2 WHERE link_id = ?1",
           nativeQuery = true)
    void updateStackOverFlowLink(Long linkId, Integer answersCount);
}
