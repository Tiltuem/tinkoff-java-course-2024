package edu.java.scrapper.repository;

import edu.java.exception.UserAlreadyRegisteredException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.scrapper.IntegrationEnvironment;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JdbcUserRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcUserRepository userRepository;
    private static final Long CHAT_ID = 123L;

    @Test
    @Transactional
    @Rollback
    void testAddUser() {
        userRepository.save(CHAT_ID);
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(4); //При создании бд добавляется 3 юзера
        assertThat(users.getLast().getChatId()).isEqualTo(CHAT_ID);
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveUser() {
        userRepository.save(CHAT_ID);
        Long userId = userRepository.findByChatId(CHAT_ID).get().getId();
        userRepository.remove(userId);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserByUserId() {
        userRepository.save(CHAT_ID);
        Long userId = userRepository.findByChatId(CHAT_ID).get().getId();

        Optional<User> user = userRepository.findById(userId);
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void testAddUserAlreadyExists() {
        assertThatThrownBy(() -> {
            userRepository.save(1L);
        }).isInstanceOf(UserAlreadyRegisteredException.class).hasMessageContaining("User with chatId = 1 already exists");
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveNonExistingUser() {
        assertThatThrownBy(() -> {
            userRepository.remove(1122L);
        }).isInstanceOf(UserIsNotRegisteredException.class).hasMessageContaining("User not found.");
    }
}
