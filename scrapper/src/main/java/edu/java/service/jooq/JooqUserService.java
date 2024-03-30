package edu.java.service.jooq;

import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jooq.repository.JooqUserRepository;
import edu.java.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import static edu.java.exception.ExceptionsString.CHAT_ID_NOT_FOUND;
import static edu.java.exception.ExceptionsString.USER_NOT_FOUND;

@RequiredArgsConstructor
public class JooqUserService implements UserService {
    private final JooqUserRepository userRepository;

    @Override
    public void addUser(Long chatId) {
        userRepository.save(chatId);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserIsNotRegisteredException(USER_NOT_FOUND.getMessage().formatted(id)));
    }

    @Override
    public List<Long> getUsersTrackLink(Long linkId) {
        return userRepository.findUsersTrackLink(linkId);
    }

    @Override
    public User getByChatId(Long chatId) {
        return userRepository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.getMessage().formatted(chatId)));
    }
}
