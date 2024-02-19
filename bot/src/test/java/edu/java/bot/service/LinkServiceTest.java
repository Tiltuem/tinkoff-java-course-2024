package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;

import edu.java.bot.repository.LinkStorage;
import edu.java.bot.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LinkServiceTest {
    private static LinkService linkService;

    @BeforeEach
    public void init() {
        linkService = new LinkServiceImpl(new LinkStorage());
    }

    @Test
    @DisplayName("testGetAllLinks")
    public void testGetAllLinks() {
        String text = (String) new SendMessage(1L, "No tracked links").getParameters().get("text");

        assertThat(linkService.getAllLink(1L).getParameters().get("text")).isEqualTo(text);
    }

    @Test
    @DisplayName("testAddLink")
    void testAddLink() {
        String text1 = (String) new SendMessage(1L, "Non-existing link").getParameters().get("text");
        String text2 = (String) new SendMessage(1L, "The site is unsupported").getParameters().get("text");
        String text3 = (String) new SendMessage(1L, "Link successfully added").getParameters().get("text");
        String text4 = (String) new SendMessage(1L, "Link successfully added").getParameters().get("text");
        String text5 = (String) new SendMessage(1L, "Link is already tracking").getParameters().get("text");

        assertThat(linkService.addLink(1L, "dada").getParameters().get("text")).isEqualTo(text1);

        assertThat(linkService.addLink(1L, "https://regex101.com/r/tM7Pmr/4").getParameters().get("text")).isEqualTo(
            text2);

        assertThat(linkService.addLink(1L, "https://github.com/Tiltuem").getParameters().get("text")).isEqualTo(text3);

        assertThat(linkService.addLink(
            1L,
            "https://stackoverflow.com/questions/14848877/uri-not-absolute-exception-getting-while-calling-restful-webservice"
        ).getParameters().get("text")).isEqualTo(text4);

        assertThat(linkService.addLink(1L, "https://github.com/Tiltuem").getParameters().get("text")).isEqualTo(text5);
    }


    @Test
    @DisplayName("testRemoveLink")
    void testRemoveLink() {
        linkService.addLink(1L, "https://github.com/Tiltuem");

        String text1 = (String) new SendMessage(1L, "Link successfully deleted").getParameters().get("text");
        String text2 = (String) new SendMessage(1L, "Link is not tracking").getParameters().get("text");


        assertThat(linkService.removeLink(1L, "https://github.com/Tiltuem").getParameters().get("text")).isEqualTo(text1);
        assertThat(linkService.removeLink(1L, "https://github.com/Tiltuem").getParameters().get("text")).isEqualTo(
            text2);
    }
}
