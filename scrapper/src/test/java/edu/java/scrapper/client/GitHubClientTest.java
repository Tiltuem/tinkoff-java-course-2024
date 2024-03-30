package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.GitHubClient;
import edu.java.client.impl.GitHubClientImpl;
import edu.java.model.response.GitHubRepositoryResponse;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
public class GitHubClientTest {
    @Test
    @DisplayName("testFetchRepo")
    public void testFetchRepo(WireMockRuntimeInfo wmRuntimeInfo) {
        GitHubClient gitHubClient = new GitHubClientImpl(wmRuntimeInfo.getHttpBaseUrl());
        stubFor(get("/repos/my/test").willReturn(ok().withHeader("Content-Type", "application/json").withBody("""
            {
              "id": 123,
              "name": "Hello-World",
              "full_name": "my/test",
              "updated_at": "2024-02-25T00:00:00Z"
            }
            """)));

        GitHubRepositoryResponse response = gitHubClient.fetchRepo("my", "test");
        OffsetDateTime result = OffsetDateTime.of(2024, 2, 25, 0, 0, 0, 0, ZoneOffset.UTC);
        assertThat(response).isNotNull();
        assertThat(response.fullName()).isEqualTo("my/test");
        assertThat(response.updatedAt()).isEqualTo(result);
    }
}
