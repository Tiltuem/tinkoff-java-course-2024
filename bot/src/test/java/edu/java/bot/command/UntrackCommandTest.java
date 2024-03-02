package edu.java.bot.command;

import edu.java.bot.command.impl.UntrackCommand;
import edu.java.bot.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;

public class UntrackCommandTest {
    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    @DisplayName("testUntrackCommand")
    void testUntrackCommand() {
        Command untrackCommand = new UntrackCommand(linkService);
        String actualCommand = untrackCommand.command();

        assertThat("/untrack").isEqualTo(actualCommand);
    }

    @Test
    @DisplayName("testUntrackCommandDescription")
    void testUntrackCommandDescription() {
        Command untrackCommand = new UntrackCommand(linkService);
        String actualDescription = untrackCommand.description();

        assertThat("Stop tracking link. Use: /untrack <LINK>").isEqualTo(actualDescription);
    }
}
