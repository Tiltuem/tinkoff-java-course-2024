package edu.java.controller;

import edu.java.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;

    @PostMapping("/add/{chatId}")
    public void registerChat(@PathVariable Long chatId) {
        userService.addUser(chatId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteChat(@PathVariable Long id) {
        userService.removeUser(id);
    }
}
