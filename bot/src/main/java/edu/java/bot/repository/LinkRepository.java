package edu.java.bot.repository;

import java.util.HashSet;

public interface LinkRepository {
    HashSet<String> findAll(Long id);

    boolean save(Long id, String link);

    boolean deleteByLink(Long id, String link);
}
