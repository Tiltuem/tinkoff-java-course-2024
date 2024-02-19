package edu.java.bot.command;

import edu.java.bot.command.impl.TrackCommand;
import edu.java.bot.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TrackCommandTest {
    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    @DisplayName("testTrackCommand")
    void testTrackCommand() {
        Command trackCommand = new TrackCommand(linkService);
        String actualCommand = trackCommand.command();

        assertThat("/track").isEqualTo(actualCommand);
    }

    @Test
    @DisplayName("testTrackCommandDescription")
    void testTrackCommandDescription() {
        Command trackCommand = new TrackCommand(linkService);
        String actualDescription = trackCommand.description();

        assertThat("Start tracking link. Use: /track <LINK>").isEqualTo(actualDescription);
    }
}
