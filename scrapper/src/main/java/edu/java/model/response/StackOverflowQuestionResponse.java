package edu.java.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowQuestionResponse(@JsonProperty("items") List<StackOverflowQuestionItemResponse> items) {

}
