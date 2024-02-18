package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.impl.StartCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private StartCommand startCommand;

    @BeforeEach
    public void setUp() {
        given(update.message()).willReturn(message);
        given(message.chat()).willReturn(chat);
        given(chat.id()).willReturn(123L);
    }

    @Test
    @DisplayName("testStartCommand")
    void testStartCommand() {
        SendMessage response = startCommand.handle(update);

        Long id = (Long) response.getParameters().get("chat_id");
        String text = (String) response.getParameters().get("text");

        assertThat(text).isEqualTo("Welcome to the LinkTrackerBot!\nUse /help for information");
        assertThat(id).isEqualTo(123);
    }
}
