package edu.java.bot.service.impl;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.LinkAlreadyTrackedException;
import edu.java.bot.exception.LinkIsNotTrackedException;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.service.LinkService;
import edu.java.bot.util.LinkValidator;
import edu.java.bot.util.SupportedWebsites;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkRepository repository;

    @Override
    public SendMessage getAllLink(Long id) {
        Set<String> links = repository.findAll(id);

        if (links.isEmpty()) {
            return new SendMessage(id, "No tracked links. Use /track to add a link");
        }

        String allLinks = IntStream.range(1, links.size() + 1)
            .mapToObj(i -> i + ". " + links.toArray()[i - 1] + ";")
            .collect(Collectors.joining("\n"));

        return new SendMessage(id, "List of tracked resources: \n\n" + allLinks).disableWebPagePreview(true);
    }

    @Override
    public SendMessage addLink(Long id, String link) {
        if (!LinkValidator.linkExists(link)) {
            return new SendMessage(id, "Sorry, this link does not exist, please try again.");
        }

        if (!LinkValidator.siteIsSupported(link)) {
            return new SendMessage(id, "The site is unsupported.\n\nList of supported sites: \n").replyMarkup(
                SupportedWebsites.getAllSupportedWebsitesKeyboard());
        }

        if (repository.save(id, link)) {
            return new SendMessage(id, "<b><i>Link successfully added!</i></b>").parseMode(ParseMode.HTML);
        }
        throw new LinkAlreadyTrackedException("Link is already tracking.");
    }

    @Override
    public SendMessage removeLink(Long id, String link) {
        if (repository.deleteByLink(id, link)) {
            return new SendMessage(id, "<b><i>Link successfully deleted!</i></b>").parseMode(ParseMode.HTML);
        }
        throw new LinkIsNotTrackedException("Link is not tracking.");
    }
}
