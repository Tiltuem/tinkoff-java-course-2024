package edu.java.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionsString {
    CHAT_ID_NOT_FOUND("ChatId = %d not found."),
    LINK_URL_NOT_FOUND("Link with url = %s not found."),
    LINK_IS_ALREADY_TRACKED("Link with URL = %s already tracked."),
    SITE_IS_ALREADY_EXISTS("Site with URL = %s already exists"),
    SITE_URL_NOT_FOUND("Site with the url = %s not found."),
    USER_NOT_FOUND("User with the id = %d not found."),
    USER_IS_ALREADY_EXISTS("User with chatId = %d already exists");

    private final String message;
}
