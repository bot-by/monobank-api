package uk.bot_by.monobank4j.api_json;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import feign.Feign;
import feign.FeignException;
import feign.http2client.Http2Client;
import feign.json.JsonDecoder;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;

@ExtendWith(MockServerExtension.class)
@Tag("slow")
public class JsonTest {

  private static final String MOCKSERVER_LOCATOR = "http://localhost:%s/";

  private static Currency currency;

  @BeforeEach
  public void setUp(MockServerClient mockServerClient) {
    currency = Feign.builder()
        .client(new Http2Client())
        .decoder(new JsonDecoder())
        .target(Currency.class,
            String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()));
    mockServerClient.reset();
  }

  @DisplayName("Get currency rates")
  @Test
  public void happyPath(MockServerClient mockServerClient) throws IOException {
    // given
    String responseBody = Files.readString(
        Path.of("src/test/resources/currency_rates/currency_rates.json"), ISO_8859_1);

    mockServerClient.when(request("/bank/currency")
            .withMethod("GET"))
        .respond(response()
            .withHeader("content-type", "application/json; charset=utf-8")
            .withBody(responseBody));

    // when
    JSONArray currencyRates = currency.getRates();

    // then
    mockServerClient.verify(request("/bank/currency"));

    assertAll("Currency rates",
        () -> assertNotNull(currencyRates, "rates"),
        () -> assertEquals(1, currencyRates.length(), "array size"),
        () -> assertEquals(BigDecimal.valueOf(27.1),
            currencyRates.getJSONObject(0).getBigDecimal("rateCross"), "cross"));
  }

  @DisplayName("Too many requests")
  @Test
  public void tooManyRequests(MockServerClient mockServerClient) throws IOException {
    // given
    String messagePattern = Files.readString(
        Path.of("src/test/resources/too_many_requests/message_pattern.txt"), ISO_8859_1);
    String responseBody = Files.readString(
        Path.of("src/test/resources/too_many_requests/too_many_requests.json"), ISO_8859_1);

    mockServerClient.when(request("/bank/currency")
            .withMethod("GET"))
        .respond(response()
            .withStatusCode(429)
            .withHeader("content-type", "application/json; charset=utf-8")
            .withHeader("reason-phrase", "Mocked")
            .withBody(responseBody));

    // when
    FeignException exception = assertThrows(FeignException.TooManyRequests.class,
        () -> currency.getRates());

    // then
    mockServerClient.verify(request("/bank/currency"));

    assertAll("API server returns error",
        () -> assertEquals(429, exception.status(), "status"),
        () -> assertThat("message", exception.getMessage(), matchesPattern(messagePattern)));
  }

}
