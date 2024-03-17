package edu.java.service.impl;

import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
            .orElseThrow(() -> new UserIsNotRegisteredException("User with the id = %d not found.".formatted(id)));
    }

    @Override
    public List<Long> getUsersTrackLink(Long linkId) {
        return repository.findUsersTrackLink(linkId);
    }
}
