package edu.java.bot.repository;

import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class ChatStorage implements ChatRepository {
    private final HashSet<Long> chats = new HashSet<>();

    @Override
    public boolean save(Long id) {
        return chats.add(id);
    }

    @Override
    public boolean findById(Long id) {
        return chats.contains(id);
    }
}
