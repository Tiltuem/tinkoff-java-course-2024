package edu.java.bot.command;

import edu.java.bot.command.impl.StartCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest {
    @Test
    @DisplayName("testListCommand")
    void testStartCommand() {
        Command startCommand = new StartCommand();
        String actualCommand = startCommand.command();

        assertThat("/start").isEqualTo(actualCommand);
    }

    @Test
    @DisplayName("testStartCommandDescription")
    void testStartCommandDescription() {
        Command startCommand = new StartCommand();
        String actualDescription = startCommand.description();

        assertThat("Start the bot and get welcome message").isEqualTo(actualDescription);
    }
}
