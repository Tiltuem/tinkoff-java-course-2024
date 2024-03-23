package edu.java.service.jpa;

import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jpa.JpaUserRepository;
import edu.java.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserService {
    private final JpaUserRepository repository;

    @Override
    public void addUser(Long chatId) {
        repository.save(User.builder().chatId(chatId).build());
    }

    @Override
    public void removeUser(Long id) {
        repository.deleteById(id);
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
        return repository.findAllByLinkId(linkId).stream().map(User::getId).collect(
            Collectors.toList());
    }
}
