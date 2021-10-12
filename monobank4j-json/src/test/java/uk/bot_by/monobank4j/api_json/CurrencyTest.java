package uk.bot_by.monobank4j.api_json;

import feign.Feign;
import feign.FeignException;
import feign.json.JsonDecoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.RequestKey;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static feign.mock.HttpMethod.GET;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("fast")
public class CurrencyTest {

	private static JsonDecoder decoder;
	private static RequestKey requestKey;

	private Currency currency;
	private MockClient mockClient;

	@BeforeAll
	public static void setUpClass() {
		decoder = new JsonDecoder();
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
		JSONArray currencyRates = currency.getRates();

		// then
		mockClient.verifyOne(GET, "/bank/currency");

		assertAll("Currency rates",
				() -> assertNotNull(currencyRates, "rates"),
				() -> assertEquals(1, currencyRates.length(), "array size"),
				() -> assertEquals(BigDecimal.valueOf(27.1), currencyRates.getJSONObject(0).getBigDecimal("rateCross"), "cross"));
	}

	@DisplayName("Too many requests")
	@Test
	public void tooManyRequests() throws IOException {
		// given
		String messagePattern = Files.readString(Path.of("src/test/resources/too_many_requests/currency_rates.txt"), ISO_8859_1);
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
				() -> assertThat("message", exception.getMessage(), matchesPattern(messagePattern)));

	}

	@DisplayName("Default instance")
	@Test
	public void defaultInstance() {
		assertNotNull(Currency.getInstance(), "Default instance");
	}

}
