package edu.java.scrapper.repository;

import edu.java.exception.UserAlreadyRegisteredException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.scrapper.IntegrationEnvironment;
import edu.java.service.UserService;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
public class UserServiceTest extends IntegrationEnvironment {
    @Autowired
    private UserService userService;

    private static final Long CHAT_ID = 123L;

    @Test
    @Transactional
    @Rollback
    void testAddUser() {
        userService.addUser(CHAT_ID);
        List<User> users = userService.getAllUsers();

        assertThat(users.size()).isEqualTo(4); //При создании бд добавляется 3 юзера
        assertThat(users.getLast().getChatId()).isEqualTo(CHAT_ID);
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveUser() {
        userService.addUser(CHAT_ID);
        Long userId = userService.getByChatId(CHAT_ID).getId();
        userService.removeUser(userId);

        List<User> users = userService.getAllUsers();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserByUserId() {
        userService.addUser(CHAT_ID);
        Long userId = userService.getByChatId(CHAT_ID).getId();

        User user = userService.getUserById(userId);
        assertThat(user.getChatId()).isEqualTo(CHAT_ID);
    }

    @Test
    @Transactional
    @Rollback
    public void testAddUserAlreadyExists() {
        assertThatThrownBy(() -> {
            userService.addUser(1L);
        }).isInstanceOf(UserAlreadyRegisteredException.class)
            .hasMessageContaining("User with chatId = 1 already exists");
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveNonExistingUser() {
        assertThatThrownBy(() -> {
            userService.removeUser(1122L);
        }).isInstanceOf(UserIsNotRegisteredException.class).hasMessageContaining("User not found.");
    }
}
