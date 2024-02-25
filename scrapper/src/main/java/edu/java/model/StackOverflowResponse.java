package edu.java.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowResponse(@JsonProperty("items") List<StackOverflowItemResponse> items) {

}
