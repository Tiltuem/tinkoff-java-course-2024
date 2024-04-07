package edu.java.bot.service.impl;

import edu.java.bot.service.Bot;
import edu.java.bot.service.LinkUpdateService;
import edu.java.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdateServiceImpl implements LinkUpdateService {
    private final Bot bot;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        String message = request.description() + "\n" + request.url();
        for (Long chatId : request.tgChatIds()) {
            bot.sendMessage(chatId, message);
        }
    }
}
