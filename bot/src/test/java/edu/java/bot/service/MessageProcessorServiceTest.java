package edu.java.bot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;
import java.util.List;
import edu.java.bot.command.Command;
import edu.java.bot.service.impl.MessageProcessorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MessageProcessorServiceTest {

    @Mock
    private Update update;
    @InjectMocks
    private MessageProcessorServiceImpl messageProcessorService;

    private final Long chatId = 123L;
    private final List<Command> commandList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        messageProcessorService = new edu.java.bot.service.impl.MessageProcessorServiceImpl(commandList);
    }

    @Test
    @DisplayName("testWithCommand")
    void testWithCommand() {
        Command command1 = mock(Command.class);
        given(command1.supports(update)).willReturn(true);
        SendMessage givenSendMessage = new SendMessage(chatId, "/start - Start the bot and get welcome message");
        given(command1.handle(update)).willReturn(givenSendMessage);

        commandList.add(command1);
        SendMessage result = messageProcessorService.process(update);
        String text = (String) result.getParameters().get("text");

        assertThat(text).isEqualTo("/start - Start the bot and get welcome message");
    }
}
