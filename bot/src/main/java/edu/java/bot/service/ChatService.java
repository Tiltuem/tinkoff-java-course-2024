package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface ChatService {
    SendMessage register(Long id);

    boolean getById(Long id);
}
