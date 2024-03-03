package edu.java.bot.service.impl;

import edu.java.bot.exception.UserAlreadyRegisteredException;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void register(Long id) {
        if (!chatRepository.save(id)) {
            throw new UserAlreadyRegisteredException("You are already working with our bot.");
        }
    }

    @Override
    public boolean getById(Long id) {
        return chatRepository.findById(id);
    }
}

