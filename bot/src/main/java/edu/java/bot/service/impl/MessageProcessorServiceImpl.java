package edu.java.bot.service.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.command.impl.StartCommand;
import edu.java.bot.exception.UserIsNotRegisteredException;
import edu.java.bot.service.ChatService;
import edu.java.bot.service.MessageProcessorService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProcessorServiceImpl implements MessageProcessorService {
    private final List<Command> commandList;
    private final ChatService chatService;

    @Override
    public List<? extends Command> commands() {
        return commandList;
    }

    @Override
    public SendMessage process(Update update) {
        if (isRegistered(update)) {
            for (Command command : commandList) {
                if (command.supports(update)) {
                    return command.handle(update);
                }
            }

            return new SendMessage(update.message().chat().id(), "Wrong command. Use /help to view a list of commands");
        }

        throw new UserIsNotRegisteredException("To be able to use the bot, you need to register");
    }

    private boolean isRegistered(Update update) {
        StartCommand startCommand = new StartCommand(chatService);
        return Objects.nonNull(update.message())
            && (chatService.getById(update.message().chat().id()) || startCommand.supports(update));
    }
}

