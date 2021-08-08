package uk.bot_by.monobank.api_gson;

import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.http2client.Http2Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@Tag("slow")
public class GsonTest {

	private static final String MOCKSERVER_LOCATOR = "http://localhost:%s/";

	private Currency currency;

	@BeforeEach
	public void setUp(MockServerClient mockServerClient) {
		currency = Feign.builder()
		                .client(new Http2Client())
		                .decoder(new GsonDecoder())
		                .target(Currency.class,
				                String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()));
		mockServerClient.reset();
	}

	@DisplayName("Get currency rates")
	@Test
	public void happyPath(MockServerClient mockServerClient) throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/currency_rates/currency_rates.json"), ISO_8859_1);

		mockServerClient.when(request("/bank/currency")
				                .withMethod("GET"))
		                .respond(response()
				                .withHeader("content-type", "application/json; charset=utf-8")
				                .withBody(responseBody));

		// when
		List<CurrencyInfo> currencyRates = currency.getRates();

		// then
		mockServerClient.verify(request("/bank/currency"));

		assertAll("Currency rates",
				() -> assertNotNull(currencyRates, "rates"),
				() -> assertThat("list size", currencyRates, hasSize(1)));

		CurrencyInfo dollarRate = currencyRates.iterator().next();

		assertAll("USD/UAH rate",
				() -> assertEquals(840, dollarRate.getCurrencyCodeA(), "dollar code"),
				() -> assertEquals(980, dollarRate.getCurrencyCodeB(), "hryvnia code"),
				() -> assertEquals(Instant.ofEpochSecond(1628370606), dollarRate.getDate(), "date"),
				() -> assertEquals(BigDecimal.valueOf(27.2), dollarRate.getRateBuy(), "buy"),
				() -> assertEquals(BigDecimal.valueOf(27.1), dollarRate.getRateCross(), "cross"),
				() -> assertEquals(BigDecimal.valueOf(27), dollarRate.getRateSell(), "sell"));
	}

	@DisplayName("Too many requests")
	@Test
	public void tooManyRequests(MockServerClient mockServerClient) throws IOException {
		// given
		String messagePattern = Files.readString(Path.of("src/test/resources/too_many_requests/message_pattern.txt"), ISO_8859_1);
		String responseBody = Files.readString(Path.of("src/test/resources/too_many_requests/too_many_requests.json"), ISO_8859_1);

		mockServerClient.when(request("/bank/currency")
				                .withMethod("GET"))
		                .respond(response()
				                .withStatusCode(429)
				                .withHeader("content-type", "application/json; charset=utf-8")
				                .withHeader("reason-phrase", "Mocked")
				                .withBody(responseBody));

		// when
		FeignException exception = assertThrows(FeignException.TooManyRequests.class, () -> currency.getRates());

		// then
		mockServerClient.verify(request("/bank/currency"));

		assertAll("API server returns error",
				() -> assertEquals(429, exception.status(), "status"),
				() -> assertThat("message", exception.getMessage(), matchesPattern(messagePattern)));
	}

}
