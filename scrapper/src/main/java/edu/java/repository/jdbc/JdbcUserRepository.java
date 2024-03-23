package edu.java.repository.jdbc;

import edu.java.exception.UserAlreadyRegisteredException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository {
    private static final String SAVE = "INSERT INTO users(chat_id) VALUES (?)";
    private static final String REMOVE = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String FIND_BY_CHAT_ID = "SELECT * FROM users WHERE chat_id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USERS_BY_LINK = "SELECT user_id FROM user_links WHERE link_id = ?";

    private static final BeanPropertyRowMapper<User> MAPPER = new BeanPropertyRowMapper<>(User.class);
    @Autowired
    private JdbcClient jdbcClient;

    public void save(Long chatId) {
        try {
            jdbcClient.sql(SAVE).param(chatId).update();
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegisteredException("User with chatId = %d already exists".formatted(chatId));
        }
    }

    public void remove(Long id) {
        if (jdbcClient.sql(REMOVE).param(id).update() == 0) {
            throw new UserIsNotRegisteredException("User not found.");
        }
    }

    public List<User> findAll() {
        return jdbcClient.sql(FIND_ALL).query(MAPPER).list();
    }

    public Optional<User> findByChatId(Long chatId) {
        try {
            return Optional.of(jdbcClient.sql(FIND_BY_CHAT_ID).param(chatId).query(MAPPER).single());
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findById(Long id) {
        try {
            return Optional.of(jdbcClient.sql(FIND_BY_ID).param(id).query(MAPPER).single());
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Long> findUsersTrackLink(Long linkId) {
        return jdbcClient.sql(FIND_USERS_BY_LINK).param(linkId).query(MAPPER).list().stream().map(User::getId).collect(
            Collectors.toList());
    }
}
