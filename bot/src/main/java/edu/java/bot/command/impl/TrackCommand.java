package edu.java.bot.command.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final LinkService linkService;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start tracking link. Use: /track <LINK>";
    }

    @Override
    public SendMessage handle(Update update) {
        String[] parts = update.message().text().split(" ");

        if (parts.length != 2) {
            return new SendMessage(update.message().chat().id(), "Wrong command. Use /track <link> ");
        }

        return linkService.addLink(update.message().chat().id(), parts[1]);
    }
}
