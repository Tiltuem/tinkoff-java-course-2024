package edu.java.repository.jooq.service;

import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jooq.repository.JooqUserRepository;
import edu.java.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
        return userRepository.findById(id).orElseThrow(() -> new UserIsNotRegisteredException("User with the id = %d not found.".formatted(id)));
    }


    @Override
    public List<Long> getUsersTrackLink(Long linkId) {
        return userRepository.findUsersTrackLink(linkId);
    }
}
