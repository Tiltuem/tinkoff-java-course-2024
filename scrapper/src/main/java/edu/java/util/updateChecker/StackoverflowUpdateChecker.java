package edu.java.util.updateChecker;

import edu.java.client.StackOverflowClient;
import edu.java.model.Link;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.model.response.StackOverflowQuestionItemResponse;
import edu.java.repository.StackOverFlowLinkRepository;
import edu.java.service.updater.StackOverFlowLinkUpdater;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackoverflowUpdateChecker<T extends StackOverFlowLinkRepository> implements UpdateChecker {
    private static final String HOST = "stackoverflow.com";
    private final StackOverflowClient stackOverflowClient;
    private final StackOverFlowLinkUpdater<T> linkUpdater;

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

        return linkUpdater.update(new StackoverflowLinkInfo(link, Optional.of(response.get().lastActivityDay()),
            response.get().answersCount()
        ));
    }

    @Override
    public boolean isAppropriateLink(Link link) {
        return link.getUrl().getHost().equals(HOST);
    }
}
