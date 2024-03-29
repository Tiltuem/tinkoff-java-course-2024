package edu.java.bot.service.impl;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.scrapper.ScrapperClient;
import edu.java.bot.service.LinkService;
import edu.java.dto.ListLinksResponse;
import edu.java.exception.CustomClientException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final ScrapperClient scrapperClient;

    @Override
    public SendMessage getAllLink(Long id) {
        ListLinksResponse response = scrapperClient.getAllListLinks(id).block();
        List<String> links = response.links().stream()
            .map(linkResponse -> linkResponse.url().toString())
            .toList();

        if (links.isEmpty()) {
            return new SendMessage(id, "No tracked links. Use /track to add a link");
        }

        String message =  IntStream.range(1, links.size() + 1)
            .mapToObj(i -> i + ". " + links.toArray()[i - 1] + ";")
            .collect(Collectors.joining("\n"));

        return new SendMessage(id, "List of tracked resources: \n\n" + message).disableWebPagePreview(true);
    }

    @Override
    public SendMessage addLink(Long id, String link) {
        try {
            scrapperClient.addLink(id, URI.create(link)).block();
        } catch (CustomClientException e) {
            return new SendMessage(id, e.getClientErrorResponse().exceptionMessage()).parseMode(ParseMode.HTML);
        }

        return new SendMessage(id, "<b><i>Link successfully added!</i></b>").parseMode(ParseMode.HTML);
    }

    @Override
    public SendMessage removeLink(Long id, String link) {
        try {
            scrapperClient.removeLink(id, URI.create(link)).block();
        } catch (CustomClientException e) {
            return new SendMessage(id, e.getClientErrorResponse().exceptionMessage()).parseMode(ParseMode.HTML);
        }

        return new SendMessage(id, "<b><i>Link successfully deleted!</i></b>").parseMode(ParseMode.HTML);
    }
}
