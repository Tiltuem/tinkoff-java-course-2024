package edu.java.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(@JsonProperty("full_name")
                                        String fullName,
                                       @JsonProperty("updated_at")
                                        OffsetDateTime updatedAt) {
}
