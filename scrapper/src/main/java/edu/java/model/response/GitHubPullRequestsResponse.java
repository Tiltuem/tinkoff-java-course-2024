package edu.java.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubPullRequestsResponse(@JsonProperty("total_count") Integer pullRequestsCount) {
}
