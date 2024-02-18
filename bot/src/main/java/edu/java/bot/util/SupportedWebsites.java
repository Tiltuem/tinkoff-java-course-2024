package edu.java.bot.util;

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
}
