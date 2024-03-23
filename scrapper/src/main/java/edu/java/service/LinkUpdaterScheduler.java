package edu.java.service;

import edu.java.bot.BotClient;
import edu.java.model.Link;
import edu.java.service.impl.JdbcLinkService;
import edu.java.service.impl.JdbcUserService;
import edu.java.util.updateChecker.UpdateChecker;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterScheduler {
    static int counter = 1;
    private final JdbcLinkService linkService;
    private final JdbcUserService userService;
    private final BotClient botClient;
    private final List<UpdateChecker> updateCheckerList;
    private final LinkUpdater linkUpdater;
    @Value("#{@schedulerCheckInterval.toMillis()}")
    private Duration checkInterval;


    @Scheduled(fixedDelayString = "#{@schedulerInvokeInterval.toMillis()}")
    public void update() {
        log.info("Scheduled method update number: " + counter);
        counter++;

        List<Link> links = linkService.findLinksForUpdate(checkInterval.getSeconds());
        for (Link link : links) {
            updateLink(link);
        }
    }

    private void updateLink(Link link) {
        for (UpdateChecker checker : updateCheckerList) {
            if (checker.isAppropriateLink(link)) {
                try {
                    Optional<String> result = checker.checkUpdates(link);
                    result.ifPresent(updateMessage -> sendUpdatesToUsers(link, updateMessage));
                    break;
                } catch (RuntimeException e) {
                    throw new RuntimeException("Incorrect link, please try again");
                }
            }
        }
    }

    private void sendUpdatesToUsers(Link link, String updateMessage) {
        List<Long> userIds = userService.getUsersTrackLink(link.getId());
        botClient.sendUpdates(link.getId(), link.getUrl().toString(), updateMessage, userIds).block();
    }
}
