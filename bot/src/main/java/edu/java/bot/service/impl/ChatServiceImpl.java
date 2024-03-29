package edu.java.bot.service.impl;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.scrapper.ScrapperClient;
import edu.java.bot.service.ChatService;
import edu.java.exception.CustomClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ScrapperClient scrapperClient;

    @Override
    public SendMessage register(Long id) {
        try {
            scrapperClient.registerChat(id).block();
        } catch (CustomClientException e) {
            return new SendMessage(
                id, e.getClientErrorResponse().exceptionMessage()
            );
        }

        return new SendMessage(
            id, "Welcome to the LinkTrackerBot!\nUse /help for information"
        );
    }

    @Override
    public boolean getById(Long id) {
        return chatRepository.findById(id);
    }
}

