package edu.java.util.updateChecker;

import edu.java.client.StackOverflowClient;
import edu.java.model.Link;
import edu.java.model.response.StackOverflowQuestionItemResponse;
import edu.java.service.LinkUpdater;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StackoverflowUpdateChecker implements UpdateChecker {
    private static final String HOST = "stackoverflow.com";
    private final StackOverflowClient stackOverflowClient;
    private final LinkUpdater linkUpdater;

    @Autowired
    public StackoverflowUpdateChecker(StackOverflowClient stackOverflowClient, LinkUpdater linkUpdater) {
        this.stackOverflowClient = stackOverflowClient;
        this.linkUpdater = linkUpdater;
    }

    @Override
    public Optional<String> checkUpdates(Link link) {
        Optional<Integer> questionId = Arrays.stream(link
                .getUrl()
                .getPath()
                .split("/"))
            .skip(2)
            .findFirst()
            .map(Integer::parseUnsignedInt);

        if (questionId.isEmpty()) {
            return Optional.empty();
        }

        Optional<StackOverflowQuestionItemResponse> response =
            stackOverflowClient.fetchQuestion(questionId.get().longValue());
        if (Objects.requireNonNull(response).isEmpty()) {
            return Optional.empty();
        }

        return linkUpdater.update(link, response.get().lastActivityDay());
    }

    @Override
    public boolean isAppropriateLink(Link link) {
        return link.getUrl().getHost().equals(HOST);
    }
}
