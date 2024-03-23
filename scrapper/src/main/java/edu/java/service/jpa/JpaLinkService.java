package edu.java.service.jpa;

import edu.java.dto.LinkResponse;
import edu.java.model.Link;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import edu.java.util.updateChecker.UpdateChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository repository;
    private final List<UpdateChecker> updateCheckerList;

    @Override
    public LinkResponse addUserLink(Long chatId, URI uri) {

    }

    @Override
    public LinkResponse removeUserLink(Long chatId, URI uri) {
        return null;
    }

    @Override
    public List<Link> getAllUserLinks(Long id) {
        return repository.findAllByUserId(id);
    }

    @Override
    public List<Link> findLinksForUpdate(Long interval) {
        return null;
    }
}
