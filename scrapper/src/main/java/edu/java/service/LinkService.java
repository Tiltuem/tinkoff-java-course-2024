package edu.java.service;

import edu.java.dto.LinkResponse;
import edu.java.model.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    LinkResponse addUserLink(Long chatId, URI uri);

    LinkResponse removeUserLink(Long chatId, URI url);

    List<Link> getAllUserLinks(Long id);

    List<Link> findLinksForUpdate(Long interval);
}
