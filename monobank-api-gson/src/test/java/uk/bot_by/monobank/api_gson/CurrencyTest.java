package uk.bot_by.monobank.api_gson;

import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.RequestKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import static feign.mock.HttpMethod.GET;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
public class CurrencyTest {

	private static GsonDecoder decoder;
	private static RequestKey requestKey;

	private Currency currency;
	private MockClient mockClient;

	@BeforeAll
	public static void setUpClass() {
		decoder = new GsonDecoder();
		requestKey = RequestKey.builder(GET, "/bank/currency").build();
	}

	@DisplayName("Get currency rates")
	@Test
	public void happyPath() throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/currency_rates/currency_rates.json"), ISO_8859_1);

		mockClient = new MockClient().ok(requestKey, responseBody);
		currency = Feign.builder()
		                .decoder(decoder)
		                .client(mockClient)
		                .target(new MockTarget<>(Currency.class));

		// when
		List<CurrencyInfo> currencyRates = currency.getRates();

		// then
		mockClient.verifyOne(GET, "/bank/currency");

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

	@DisplayName("Missed fields")
	@Test
	public void missedFields() throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/missed_fields/currency_rates.json"), ISO_8859_1);

		mockClient = new MockClient().ok(requestKey, responseBody);
		currency = Feign.builder()
		                .decoder(decoder)
		                .client(mockClient)
		                .target(new MockTarget<>(Currency.class));

		// when
		List<CurrencyInfo> currencyRates = currency.getRates();

		// then
		mockClient.verifyOne(GET, "/bank/currency");

		assertAll("Currency rates",
				() -> assertNotNull(currencyRates, "rates"),
				() -> assertThat("list size", currencyRates, hasSize(1)));

		CurrencyInfo dollarRate = currencyRates.iterator().next();

		assertAll("USD/UAH rate",
				() -> assertEquals(840, dollarRate.getCurrencyCodeA(), "dollar code"),
				() -> assertEquals(980, dollarRate.getCurrencyCodeB(), "hryvnia code"),
				() -> assertEquals(Instant.ofEpochSecond(1628370606), dollarRate.getDate(), "date"),
				() -> assertNull(dollarRate.getRateBuy(), "buy"),
				() -> assertEquals(BigDecimal.valueOf(27.1), dollarRate.getRateCross(), "cross"),
				() -> assertNull(dollarRate.getRateSell(), "sell"));
	}

	@DisplayName("Too many requests")
	@Test
	public void tooManyRequests() throws IOException {
		// given
		String expectedMessage = Files.readString(Path.of("src/test/resources/too_many_requests/message_pattern.txt"), ISO_8859_1);
		String responseBody = Files.readString(Path.of("src/test/resources/too_many_requests/too_many_requests.json"), ISO_8859_1);

		mockClient = new MockClient().add(requestKey, 429, responseBody);
		currency = Feign.builder()
		                .decoder(decoder)
		                .client(mockClient)
		                .target(new MockTarget<>(Currency.class));

		// when
		FeignException exception = assertThrows(FeignException.TooManyRequests.class, () -> currency.getRates(), "error");

		// then
		mockClient.verifyOne(GET, "/bank/currency");

		assertAll("API server returns error",
				() -> assertEquals(429, exception.status(), "status"),
				() -> assertThat("message", exception.getMessage(), matchesPattern(expectedMessage)));

	}

	@DisplayName("Default instance")
	@Test
	public void defaultInstance() {
		assertNotNull(Currency.getInstance(), "Default instance");
	}

}
