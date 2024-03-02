package edu.java.bot.service;

import edu.java.bot.util.LinkValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LinkValidatorTest {
    @Test
    @DisplayName("testLinkExists")
    void testLinkExists() {
        String firstLink = "abc";
        String secondLink = "https://edu.tinkoff.ru/";

        assertThat(LinkValidator.linkExists(firstLink)).isFalse();
        assertThat(LinkValidator.linkExists(secondLink)).isTrue();
    }

    @Test
    @DisplayName("testSiteIsSupported")
    void testSiteIsSupported() {
        String firstLink = "https://edu.tinkoff.ru/";
        String secondLink = "https://github.com/Tiltuem";


        assertThat(LinkValidator.siteIsSupported(firstLink)).isFalse();
        assertThat(LinkValidator.siteIsSupported(secondLink)).isTrue();
    }

}
