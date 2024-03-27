package edu.java.repository.jpa;

import edu.java.model.Link;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.repository.StackOverFlowLinkRepository;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaStackOverFlowLinkRepository extends StackOverFlowLinkRepository {
    private final JpaLinkRepository repository;

    @Override
    @Transactional
    public StackoverflowLinkInfo updateLink(StackoverflowLinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        link.setLastUpdate(linkInfo.getLastUpdate().get());
        link.setLastCheck(OffsetDateTime.now());
        repository.save(link);

        StackoverflowLinkInfo oldInfo;

        Integer answersCount = repository.findAnswersCountAtLink(link.getId());
        oldInfo = new StackoverflowLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), answersCount);

        if (!Objects.equals(answersCount, linkInfo.getAnswersCount())) {
            repository.updateStackOverFlowLink(link.getId(), linkInfo.getAnswersCount());

        }

        return oldInfo;
    }
}
