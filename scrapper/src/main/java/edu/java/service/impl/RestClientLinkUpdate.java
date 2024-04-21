package edu.java.service.impl;

import edu.java.bot.BotClient;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkUpdateService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestClientLinkUpdate implements LinkUpdateService {
    private final BotClient botClient;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        botClient.sendUpdates(request).block();
    }
}
