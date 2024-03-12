package edu.java.bot.service;

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
        assertThat(linkService.getAllLink(1L).getParameters().get("text")).isEqualTo(
            "No tracked links. Use /track to add a link");
    }

    @Test
    @DisplayName("testAddLink")
    void testAddLink() {
        assertThat(linkService.addLink(1L, "dada").getParameters().get("text")).isEqualTo(
            "Sorry, this link does not exist, please try again.");
        assertThat(linkService.addLink(1L, "https://regex101.com/r/tM7Pmr/4").getParameters().get("text")).isEqualTo(
            "The site is unsupported.\n\nList of supported sites: \n");
        assertThat(linkService.addLink(1L, "https://github.com/Tiltuem").getParameters().get("text")).isEqualTo(
            "<b><i>Link successfully added!</i></b>");
    }

    @Test
    @DisplayName("testRemoveLink")
    void testRemoveLink() {
        linkService.addLink(1L, "https://github.com/Tiltuem");

        assertThat(linkService.removeLink(1L, "https://github.com/Tiltuem").getParameters().get("text")).isEqualTo(
            "<b><i>Link successfully deleted!</i></b>");
    }
}
