package edu.java.bot.scrapper;

import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import java.net.URI;
import reactor.core.publisher.Mono;

public interface ScrapperClient {
    Mono<Void> registerChat(Long chatId);

    Mono<Void> deleteChat(Long chatId);

    Mono<ListLinksResponse> getAllListLinks(Long chatId);

    Mono<LinkResponse> addLink(Long chatId, URI link);

    Mono<LinkResponse> removeLink(Long chatId, URI link);
}
