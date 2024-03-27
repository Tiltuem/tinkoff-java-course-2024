package edu.java.service.updater;

import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.repository.StackOverFlowLinkRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StackOverFlowLinkUpdater<T extends StackOverFlowLinkRepository>  {
    private final T repository;

    public Optional<String> update(StackoverflowLinkInfo linkInfo) {
        StackoverflowLinkInfo oldInfo = repository.updateLink(linkInfo);

        String response = null;

        if (linkInfo.getLastUpdate().isPresent() && oldInfo.getLastUpdate().isPresent()
            && linkInfo.getLastUpdate().get().isAfter(oldInfo.getLastUpdate()
            .get())) {
            response = "Link updated";

            if (!Objects.equals(
                linkInfo.getAnswersCount(),
                oldInfo.getAnswersCount()
            )) {
                response += ": has new answers";
            }
        }

        return Optional.ofNullable(response);
    }
}
