package edu.java.controller;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import edu.java.service.jdbc.JdbcLinkService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final JdbcLinkService linkService;

    @GetMapping("/get-all")
    public ListLinksResponse getAllUserLinks(@RequestHeader(name = "Tg-Chat-Id") Long id) {
        List<LinkResponse> links =
            linkService.getAllUserLinks(id).stream().map(link -> new LinkResponse(link.getId(), link.getUrl()))
                .toList();
        return new ListLinksResponse(links, links.size());
    }

    @PostMapping("/add")
    public LinkResponse addUserLink(
        @RequestHeader(name = "Tg-Chat-Id") Long id,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        return linkService.addUserLink(id, URI.create(addLinkRequest.link()));
    }

    @DeleteMapping("/delete")
    public LinkResponse removeUserLink(
        @RequestHeader(name = "Tg-Chat-Id") Long id,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        return linkService.removeUserLink(id, removeLinkRequest.link());
    }
}
