package edu.java.bot.util;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public enum SupportedWebsites {
    GITHUB("github.com"),
    STACKOVERFLOW("stackoverflow.com");

    private final String url;

    SupportedWebsites(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static InlineKeyboardMarkup getAllSupportedWebsitesKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

        for (SupportedWebsites websites : SupportedWebsites.values()) {
            inlineKeyboard.addRow(new InlineKeyboardButton(websites.name()).url(websites.getUrl()));
        }

        return inlineKeyboard;
    }
}
