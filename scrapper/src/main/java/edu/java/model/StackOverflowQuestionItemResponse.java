package edu.java.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.java.util.OffsetDateTimeMapper;
import java.time.OffsetDateTime;

public record StackOverflowQuestionItemResponse(@JsonProperty("title")
                                        String title,
                                                @JsonProperty("last_activity_date")
                                        @JsonDeserialize(using = OffsetDateTimeMapper.class)
                                        OffsetDateTime lastActivityDay) {
}
