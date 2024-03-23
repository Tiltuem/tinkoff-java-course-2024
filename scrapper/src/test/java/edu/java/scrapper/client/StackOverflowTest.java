package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.StackOverflowClientImpl;
import edu.java.model.response.StackOverflowQuestionItemResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;


@WireMockTest
public class StackOverflowTest {
    @Test
    @DisplayName("testFetchRepo")
    public void testFetchRepo(WireMockRuntimeInfo wmRuntimeInfo) {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(wmRuntimeInfo.getHttpBaseUrl());
        stubFor(get("/questions/1").willReturn(ok().withHeader("Content-Type", "application/json").withBody("""
            {
               "items": [
                 {
                   "tags": [
                     "github"
                   ],
                   "owner": {
                     "account_id": 4706306
                   },
                   "last_activity_date": 1676925493,
                   "creation_date": 1594241851,
                   "question_id": 62803531,
                   "content_license": "CC BY-SA 4.0",
                   "title": "Repository Name as a GitHub Action environment variable?"
                 }
               ],
               "has_more": false,
               "quota_max": 10000,
               "quota_remaining": 9991
             }
            """)));

        Optional<StackOverflowQuestionItemResponse> response = stackOverflowClient.fetchQuestion(1L);
        OffsetDateTime result = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1676925493), ZoneOffset.UTC);

        assertThat(response).isNotNull();
        assertThat(response.get().title()).isEqualTo("Repository Name as a GitHub Action environment variable?");
        assertThat(response.get().lastActivityDay()).isEqualTo(result);
    }
}
