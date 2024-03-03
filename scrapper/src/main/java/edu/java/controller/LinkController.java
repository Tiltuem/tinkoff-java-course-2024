package edu.java.controller;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
public class LinkController {
    @GetMapping
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader(name = "Tg-Chat-Id") Long id) {
        ListLinksResponse links = new ListLinksResponse(List.of(new LinkResponse(id, URI.create("example.com"))), 1);
        return ResponseEntity.ok(links);
    }

    @PostMapping
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader(name = "Tg-Chat-Id") Long id,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        LinkResponse linkResponse = new LinkResponse(id, URI.create(addLinkRequest.link()));
        return ResponseEntity.ok(linkResponse);
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> removeLink(
        @RequestHeader(name = "Tg-Chat-Id") Long id,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) {
        LinkResponse linkResponse = new LinkResponse(id, removeLinkRequest.link());
        return ResponseEntity.ok(linkResponse);
    }
}
