package edu.java.bot.service.impl;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.service.LinkService;
import edu.java.bot.util.LinkValidator;
import java.util.Set;
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
            return new SendMessage(id, "No tracked links");
        }

        return new SendMessage(id, String.join("\n", links));
    }

    @Override
    public SendMessage addLink(Long id, String link) {
        if (!LinkValidator.linkExists(link)) {
            return new SendMessage(id, "Non-existing link");
        }

        if (!LinkValidator.siteIsSupported(link)) {
            return new SendMessage(id, "The site is unsupported");
        }

        if (repository.save(id, link)) {
            return new SendMessage(id, "Link successfully added");
        }

        return new SendMessage(id, "Link is already tracking");
    }

    @Override
    public SendMessage removeLink(Long id, String link) {
        if (repository.deleteByLink(id, link)) {
            return new SendMessage(id, "Link successfully deleted");
        }
        return new SendMessage(id, "Link is not tracking");
    }
}
