package edu.java.bot.command;


import edu.java.bot.command.impl.ListCommand;
import edu.java.bot.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {
    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    @DisplayName("testListCommand")
    void testListCommand() {
        Command helpCommand = new ListCommand(linkService);
        String actualCommand = helpCommand.command();

        assertThat("/list").isEqualTo(actualCommand);
    }

    @Test
    @DisplayName("testListCommandDescription")
    void testListCommandDescription() {
        Command helpCommand = new ListCommand(linkService);
        String actualDescription = helpCommand.description();

        assertThat("Outputs a list of tracked links").isEqualTo(actualDescription);
    }
}
