package edu.java.bot.service.impl;

import edu.java.bot.repository.ChatRepository;
import edu.java.bot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public boolean register(Long id) {
        return chatRepository.save(id);
    }

    @Override
    public boolean getById(Long id) {
        return chatRepository.findById(id);
    }
}

