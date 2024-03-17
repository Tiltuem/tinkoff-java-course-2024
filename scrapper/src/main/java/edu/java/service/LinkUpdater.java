package edu.java.service;

import edu.java.model.Link;
import java.time.LocalDateTime;
import java.util.Optional;

public interface LinkUpdater {
    Optional<String> update(Link link, LocalDateTime updateTime);
}
