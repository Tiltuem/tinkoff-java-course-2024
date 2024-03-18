package edu.java.model;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime lastUpdate;
    private Long siteId;
}
