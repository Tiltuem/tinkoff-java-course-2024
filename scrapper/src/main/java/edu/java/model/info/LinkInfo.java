package edu.java.model.info;

import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class LinkInfo {
    private Link link;
    private Optional<OffsetDateTime> lastUpdate;
}
