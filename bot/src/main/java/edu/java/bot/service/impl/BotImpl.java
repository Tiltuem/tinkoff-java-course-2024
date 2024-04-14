package edu.java.bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.Bot;
import edu.java.bot.service.MessageProcessorService;
import io.micrometer.core.instrument.Counter;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BotImpl implements Bot {
    private final TelegramBot telegramBot;
    private final MessageProcessorService messageProcessorService;
    private final Counter processedMessageCounter;

    public BotImpl(
        ApplicationConfig config,
        MessageProcessorService messageProcessorService,
        Counter processedMessageCounter
    ) {
        this.telegramBot = new TelegramBot(config.telegramToken());
        this.messageProcessorService = messageProcessorService;
        this.processedMessageCounter = processedMessageCounter;
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            telegramBot.execute(messageProcessorService.process(update));
            processedMessageCounter.increment();
        }


        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        telegramBot.execute(new SendMessage(chatId, text));
    }

    @Override
    @PostConstruct
    public void start() {
        createMenu();
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }

    private void createMenu() {
        List<? extends Command> commandList = messageProcessorService.commands();

        telegramBot.execute(new SetMyCommands(commandList.stream()
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new)));
    }
}
