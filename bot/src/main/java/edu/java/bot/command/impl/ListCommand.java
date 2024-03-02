package edu.java.bot.command.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final LinkService linkService;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Outputs a list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        return linkService.getAllLink(update.message().chat().id());
    }
}
