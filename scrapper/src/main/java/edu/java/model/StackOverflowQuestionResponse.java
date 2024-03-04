package edu.java.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowQuestionResponse(@JsonProperty("items") List<StackOverflowQuestionItemResponse> items) {

}
