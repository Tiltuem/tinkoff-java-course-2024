package edu.java.service;

import edu.java.model.User;
import java.util.List;

public interface UserService {
    void addUser(Long chatId);

    void removeUser(Long id);

    List<User> getAllUsers();

    User getUserById(Long id);

    List<Long> getUsersTrackLink(Long linkId);

    User getByChatId(Long chatId);
}
