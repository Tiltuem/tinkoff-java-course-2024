package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.exception.UserIsNotRegisteredException;
import edu.java.bot.repository.ChatStorage;
import edu.java.bot.service.impl.ChatServiceImpl;
import edu.java.bot.service.impl.MessageProcessorServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MessageProcessorServiceTest {
    @Mock
    private Update update;
    private final ChatService chatService = new ChatServiceImpl(new ChatStorage());

    @InjectMocks
    private MessageProcessorServiceImpl messageProcessorService;

    private final List<Command> commandList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        messageProcessorService = new MessageProcessorServiceImpl(commandList, chatService);
    }

    @Test
    @DisplayName("testWithCommand")
    void testWithCommand() {
        assertThatThrownBy(() -> {
            messageProcessorService.process(update);
        }).isInstanceOf(UserIsNotRegisteredException.class).hasMessageContaining("To be able to use the bot, you need to register");
    }
}
