package edu.java.bot.service.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.service.MessageProcessorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProcessorServiceImpl implements MessageProcessorService {
    private final List<Command> commandList;


    @Override
    public List<? extends Command> commands() {
        return commandList;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commandList) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }

        return new SendMessage(1, "Wrong command. Use /help to view a list of commands");
    }
}
