package edu.java.bot.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.net.URI;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:test")
public class RestScrapperClientTest {
    private static final String CHAT_ENDPOINT_PREFIX = "/tg-chat/";
    private static final String LINKS_ENDPOINT = "/links";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private static final Long chatId = 123L;
    private static WireMockServer wireMockServer;
    @Autowired
    private RestScrapperClient restScrapperClient;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(8088);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("testRegisterChat")
    public void testRegisterChat() {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(CHAT_ENDPOINT_PREFIX + chatId))
            .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        StepVerifier.create(restScrapperClient.registerChat(chatId))
            .verifyComplete();

        WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo(CHAT_ENDPOINT_PREFIX + chatId)));
    }

    @Test
    @DisplayName("testDeleteChat")
    public void testDeleteChat() {
        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo(CHAT_ENDPOINT_PREFIX + chatId))
            .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        StepVerifier.create(restScrapperClient.registerChat(chatId))
            .verifyComplete();

        WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo(CHAT_ENDPOINT_PREFIX + chatId)));
    }

    @Test
    @DisplayName("testAddLink")
    public void testAddLink() {
        URI link = URI.create("https://example.com");
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(LINKS_ENDPOINT))
            .withHeader(TG_CHAT_ID_HEADER, WireMock.equalTo(chatId.toString()))
            .withRequestBody(equalToJson("{\"link\": \"https://example.com\"}"))
            .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"id\": 1, \"url\": \"https://example.com\"}")));

        StepVerifier.create(restScrapperClient.addLink(chatId, link))
            .expectNextMatches(response -> {
                assertThat(response.id()).isEqualTo(1);
                assertThat(response.url().toString()).isEqualTo("https://example.com");
                return true;
            })
            .verifyComplete();

        WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo(LINKS_ENDPOINT))
            .withHeader(TG_CHAT_ID_HEADER, WireMock.equalTo(chatId.toString()))
            .withRequestBody(equalToJson("{\"link\": \"https://example.com\"}")));
    }

    @Test
    @DisplayName("testRemoveLink")
    public void testRemoveLink() {
        URI link = URI.create("https://example.com");
        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo(LINKS_ENDPOINT))
            .withHeader(TG_CHAT_ID_HEADER, WireMock.equalTo(chatId.toString()))
            .withRequestBody(equalToJson("{\"link\": \"https://example.com\"}"))
            .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"id\": 1, \"url\": \"https://example.com\"}")));

        StepVerifier.create(restScrapperClient.removeLink(chatId, link))
            .expectNextMatches(response -> {
                assertThat(response.id()).isEqualTo(1);
                assertThat(response.url().toString()).isEqualTo("https://example.com");
                return true;
            })
            .verifyComplete();

        WireMock.verify(WireMock.deleteRequestedFor(WireMock.urlEqualTo(LINKS_ENDPOINT))
            .withHeader(TG_CHAT_ID_HEADER, WireMock.equalTo(chatId.toString()))
            .withRequestBody(equalToJson("{\"link\": \"https://example.com\"}")));
    }
}

