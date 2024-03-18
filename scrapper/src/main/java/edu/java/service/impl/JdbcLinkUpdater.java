package edu.java.service.impl;

import edu.java.model.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.service.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcLinkRepository linkRepository;

    @Override
    public Optional<String> update(Link link, OffsetDateTime updateTime) {
        boolean isUpdated = linkRepository.updateLink(link, updateTime);
        String ans = isUpdated ? "Repository updated" : null;
        return Optional.ofNullable(ans);
    }
}
