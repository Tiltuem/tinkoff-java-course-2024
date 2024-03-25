package edu.java.model.info;

import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GithubLinkInfo extends LinkInfo {
    private Integer pullRequestsCount;

    public GithubLinkInfo(Link link, Optional<OffsetDateTime> updateTime, Integer pullRequestsCount) {
        super(link, updateTime);
        this.pullRequestsCount = pullRequestsCount;
    }
}
