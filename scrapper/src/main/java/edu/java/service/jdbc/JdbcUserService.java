package edu.java.service.jdbc;

import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import static edu.java.exception.ExceptionsString.CHAT_ID_NOT_FOUND;
import static edu.java.exception.ExceptionsString.USER_NOT_FOUND;

@RequiredArgsConstructor
public class JdbcUserService implements UserService {
    private final JdbcUserRepository repository;

    @Override
    public void addUser(Long chatId) {
        repository.save(chatId);
    }

    @Override
    public void removeUser(Long id) {
        repository.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserIsNotRegisteredException(USER_NOT_FOUND.getMessage().formatted(id)));
    }

    @Override
    public List<Long> getUsersTrackLink(Long linkId) {
        return repository.findUsersTrackLink(linkId);
    }

    @Override
    public User getByChatId(Long chatId) {
        return repository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.getMessage().formatted(chatId)));
    }
}
