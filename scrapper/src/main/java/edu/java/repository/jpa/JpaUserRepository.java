package edu.java.repository.jpa;

import edu.java.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT user_id FROM user_links WHERE link_id = ?1",
           nativeQuery = true)
    List<User> findAllByLinkId(Long linkId);

    Optional<User> findByChatId(Long chatId);
}
