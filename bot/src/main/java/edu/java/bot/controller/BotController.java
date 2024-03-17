package edu.java.bot.controller;

import edu.java.bot.service.Bot;
import edu.java.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final Bot bot;

    @PostMapping(value = "/updates", produces = {"application/json"}, consumes = {"application/json"})
    public void processUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        String message = linkUpdateRequest.description() + "\n" + linkUpdateRequest.url();
        for (Long chatId : linkUpdateRequest.tgChatIds()) {
            bot.sendMessage(chatId, message);
        }
    }
}
