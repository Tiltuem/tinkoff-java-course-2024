package edu.java.bot.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LinkStorage implements LinkRepository {
    private final Map<Long, HashSet<String>> links = new HashMap<>();

    @Override
    public HashSet<String> findAll(Long id) {
        if (links.containsKey(id)) {
            return links.get(id);
        }
        return new HashSet<>();
    }

    @Override
    public boolean save(Long id, String link) {
        return links.computeIfAbsent(id, x -> new HashSet<>()).add(link);
    }

    @Override
    public boolean deleteByLink(Long id, String link) {
        if (links.containsKey(id)) {
            return links.get(id).remove(link);
        }
        return false;
    }
}
