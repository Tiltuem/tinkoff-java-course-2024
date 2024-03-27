package edu.java.repository.jdbc;

import edu.java.model.Link;
import edu.java.model.info.StackoverflowLinkInfo;
import edu.java.repository.StackOverFlowLinkRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JdbcStackOverFlowLinkRepository extends StackOverFlowLinkRepository {
    private static final String UPDATE_LAST_CHECK_LINK =
        "UPDATE links SET last_check = NOW()::timestamp, last_update = ? WHERE id = ?";
    private final JdbcClient jdbcClient;

    @Override
    @Transactional
    public StackoverflowLinkInfo updateLink(StackoverflowLinkInfo linkInfo) {
        Link link = linkInfo.getLink();
        jdbcClient.sql(UPDATE_LAST_CHECK_LINK).param(linkInfo.getLastUpdate().get()).param(link.getId()).update();
        StackoverflowLinkInfo oldInfo;

        String find = "SELECT answers_count FROM links "
            + "INNER JOIN stackoverflow_links ON id = link_id WHERE link_id = ? FOR UPDATE";
        oldInfo = jdbcClient.sql(find).param(link.getId()).query((rs, rowNum) -> {
            Integer answersCount = rs.getInt("answers_count");
            return new StackoverflowLinkInfo(link, Optional.ofNullable(link.getLastUpdate()), answersCount);
        }).single();

        if (!Objects.equals(oldInfo.getAnswersCount(), linkInfo.getAnswersCount())) {
            String update = "UPDATE stackoverflow_links SET answers_count = ? WHERE link_id = ?";
            jdbcClient.sql(update).param(linkInfo.getAnswersCount(), link.getId());
        }

        return oldInfo;
    }
}
