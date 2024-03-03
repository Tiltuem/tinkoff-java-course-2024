package edu.java.bot.command.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ChatService chatService;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start the bot and get welcome message";
    }

    @Override
    public SendMessage handle(Update update) {
        chatService.register(update.message().chat().id());
        return new SendMessage(
            update.message().chat().id(),
            "Welcome to the LinkTrackerBot!\nUse /help for information"
        );
    }
}
