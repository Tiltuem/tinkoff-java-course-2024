package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;


public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String command = update.message().text().split(" ")[0];
            return command().equalsIgnoreCase(command);
        }
        return false;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
