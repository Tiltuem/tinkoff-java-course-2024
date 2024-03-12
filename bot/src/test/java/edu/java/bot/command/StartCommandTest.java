package edu.java.bot.command;

import edu.java.bot.command.impl.StartCommand;
import edu.java.bot.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class  StartCommandTest {
    @Mock
    private ChatService chatService;
    @Test
    @DisplayName("testListCommand")
    void testStartCommand() {
        Command startCommand = new StartCommand(chatService);
        String actualCommand = startCommand.command();

        assertThat("/start").isEqualTo(actualCommand);
    }

    @Test
    @DisplayName("testStartCommandDescription")
    void testStartCommandDescription() {
        Command startCommand = new StartCommand(chatService);
        String actualDescription = startCommand.description();

        assertThat("Start the bot and get welcome message").isEqualTo(actualDescription);
    }
}
