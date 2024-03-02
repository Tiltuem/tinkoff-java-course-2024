package edu.java.bot.repository;


public interface ChatRepository {
    boolean save(Long id);

    boolean findById(Long id);
}
