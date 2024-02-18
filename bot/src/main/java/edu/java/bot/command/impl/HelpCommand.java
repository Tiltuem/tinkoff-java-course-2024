package edu.java.bot.command.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final List<Command> commandList;
    private static String message;

    @PostConstruct
    private void init() {
        StringBuilder sb = new StringBuilder();
        for (Command command : commandList) {
            sb.append(String.format("%s - %s\n", command.command(), command.description()));
        }

        message = sb.toString();
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Show all available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), message);
    }
}
