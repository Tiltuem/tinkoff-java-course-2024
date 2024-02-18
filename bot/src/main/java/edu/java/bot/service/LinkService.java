package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface LinkService {
    SendMessage getAllLink(Long id);

    SendMessage addLink(Long id, String link);

    SendMessage removeLink(Long id, String link);
}
