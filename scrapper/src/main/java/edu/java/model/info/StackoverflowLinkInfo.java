package edu.java.model.info;

import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StackoverflowLinkInfo extends LinkInfo {
    private Integer answersCount;

    public StackoverflowLinkInfo(Link link, Optional<OffsetDateTime> updateTime, Integer answersCount) {
        super(link, updateTime);
        this.answersCount = answersCount;
    }
}
