package edu.java.bot.command;

import edu.java.bot.command.impl.HelpCommand;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class HelpCommandTest {
    @Test
    @DisplayName("testHelpCommand")
    void testHelpCommand() {
        Command helpCommand = new HelpCommand(new ArrayList<>());

        String actualCommand = helpCommand.command();

        assertThat("/help").isEqualTo(actualCommand);
    }

    @Test
    @DisplayName("testHelpCommandDescription")
    void testHelpCommandDescription() {
        Command helpCommand = new HelpCommand(new ArrayList<>());

        String actualDescription = helpCommand.description();

        assertThat("Show all available commands").isEqualTo(actualDescription);
    }
}
