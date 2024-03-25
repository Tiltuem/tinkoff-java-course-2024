package edu.java.service.jpa;

import edu.java.exception.ChatIdNotFoundException;
import edu.java.exception.UserAlreadyRegisteredException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jpa.JpaUserRepository;
import edu.java.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import static edu.java.exception.ExceptionsString.CHAT_ID_NOT_FOUND;
import static edu.java.exception.ExceptionsString.USER_IS_ALREADY_EXISTS;
import static edu.java.exception.ExceptionsString.USER_NOT_FOUND;

@RequiredArgsConstructor
public class JpaUserService implements UserService {
    private final JpaUserRepository repository;

    @Override
    public void addUser(Long chatId) {
        if (repository.findByChatId(chatId).isPresent()) {
            throw new UserAlreadyRegisteredException(USER_IS_ALREADY_EXISTS.getMessage().formatted(chatId));
        }

        repository.save(User.builder().chatId(chatId).build());
    }

    @Override
    public void removeUser(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new UserIsNotRegisteredException("User not found.");
        }

        repository.deleteById(id);
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
        return repository.findAllByLinkId(linkId).stream().map(User::getId).collect(
            Collectors.toList());
    }

    @Override
    public User getByChatId(Long chatId) {
        return repository.findByChatId(chatId)
            .orElseThrow(() -> new ChatIdNotFoundException(CHAT_ID_NOT_FOUND.getMessage().formatted(chatId)));
    }
}
