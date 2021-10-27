package uk.bot_by.monobank4j.api_gson;

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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@Tag("slow")
public class GsonTest {

	private static final String MOCKSERVER_LOCATOR = "http://localhost:%s/";

	@BeforeEach
	public void setUp(MockServerClient mockServerClient) {
		mockServerClient.reset();
	}

	@DisplayName("Get currency rates")
	@Test
	public void getCurrencyRates(MockServerClient mockServerClient) throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/currency_rates/currency_rates.json"), UTF_8);

		mockServerClient.when(request("/bank/currency")
				                .withMethod("GET"))
		                .respond(response()
				                .withHeader("content-type", "application/json; charset=utf-8")
				                .withBody(responseBody));

		Currency currency = Feign.builder()
		                         .client(new Http2Client())
		                         .decoder(new GsonDecoder())
		                         .target(Currency.class, String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()));

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

	@DisplayName("Too many requests of currency rates")
	@Test
	public void tooManyRequestsOfCurrencyRates(MockServerClient mockServerClient) throws IOException {
		// given
		String messagePattern = Files.readString(Path.of("src/test/resources/too_many_requests/currency_rates.txt"), UTF_8);
		String responseBody = Files.readString(Path.of("src/test/resources/too_many_requests/too_many_requests.json"), UTF_8);

		mockServerClient.when(request("/bank/currency")
				                .withMethod("GET"))
		                .respond(response()
				                .withStatusCode(429)
				                .withHeader("content-type", "application/json; charset=utf-8")
				                .withHeader("reason-phrase", "Mocked")
				                .withBody(responseBody));

		Currency currency = Feign.builder()
		                         .client(new Http2Client())
		                         .decoder(new GsonDecoder())
		                         .target(Currency.class, String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()));

		// when
		FeignException exception = assertThrows(FeignException.TooManyRequests.class, currency::getRates);

		// then
		mockServerClient.verify(request("/bank/currency"));

		assertAll("API server returns error",
				() -> assertEquals(429, exception.status(), "status"),
				() -> assertThat("message", exception.getMessage(), matchesPattern(messagePattern)));
	}

	@DisplayName("Get client information")
	@Test
	public void getClientInformation(MockServerClient mockServerClient) throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/client-info/client-info.json"), UTF_8);

		mockServerClient.when(request("/personal/client-info")
				                .withMethod("GET"))
		                .respond(response()
				                .withHeader("content-type", "application/json; charset=utf-8")
				                .withBody(responseBody));

		Personal personal = Feign.builder()
		                         .client(new Http2Client())
		                         .decoder(new GsonDecoder())
		                         .target(Personal.class, String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()));

		// when
		ClientInfo clientInfo = personal.getClientInfo();

		// then
		mockServerClient.verify(request("/personal/client-info"));

		assertAll("Client information",
				() -> assertNotNull(clientInfo, "client information"),
				() -> assertEquals("vF8Sml8ukr", clientInfo.getClientId(), "client id"),
				() -> assertEquals("Огієнко Мар'ян", clientInfo.getName(), "name"),
				() -> assertTrue(clientInfo.getWebhookLocator().isEmpty(), "webhook"),
				() -> assertEquals("xyz", clientInfo.getPermissions(), "permissions"),
				() -> assertThat("accounts", clientInfo.getAccounts(), hasSize(2)));

		Iterator<ClientInfo.Account> accounts = clientInfo.getAccounts().iterator();
		ClientInfo.Account black = accounts.next();
		ClientInfo.Account business = accounts.next();

		assertAll("Black",
				() -> assertEquals("gyY61faILGJUgkWneaO4oL", black.getId(), "id"),
				() -> assertEquals("UrggEjCAz0", black.getSendId(), "send id"),
				() -> assertEquals(980, black.getCurrencyCode(), "currency code"),
				() -> assertEquals("UAH", black.getCashbackType(), "cashback"),
				() -> assertEquals(BigInteger.valueOf(2000000), black.getBalance(), "balance"),
				() -> assertEquals(BigInteger.valueOf(1000000), black.getCreditLimit(), "credit limit"),
				() -> assertEquals("black", black.getType(), "type"),
				() -> assertEquals("UA203975964178795464987195612", black.getAccount(), "iban"),
				() -> assertThat("PANs", black.getMaskedPrimaryAccounts(), hasSize(1)),
				() -> assertEquals("557841******6454", black.getMaskedPrimaryAccounts().iterator().next(), "masked PAN"));
		assertAll("FOP",
				() -> assertEquals("huv6fBmk6PCk14M56WJAWs", business.getId(), "id"),
				() -> assertTrue(business.getSendId().isEmpty(), "send id"),
				() -> assertEquals(980, business.getCurrencyCode(), "currency code"),
				() -> assertTrue(business.getCashbackType().isEmpty(), "cashback"),
				() -> assertEquals(BigInteger.ZERO, business.getBalance(), "balance"),
				() -> assertEquals(BigInteger.ZERO, business.getCreditLimit(), "credit limit"),
				() -> assertEquals("fop", business.getType(), "type"),
				() -> assertEquals("UA263985562455143666954374231", business.getAccount(), "iban"),
				() -> assertThat("PANs", business.getMaskedPrimaryAccounts(), empty()));
	}

	@DisplayName("Too many requests of client information")
	@Test
	public void tooManyRequestOfClientInformation(MockServerClient mockServerClient) throws IOException {
		// given
		String messagePattern = Files.readString(Path.of("src/test/resources/too_many_requests/client-info.txt"), UTF_8);
		String responseBody = Files.readString(Path.of("src/test/resources/too_many_requests/too_many_requests.json"), UTF_8);

		mockServerClient.when(request("/personal/client-info")
				                .withMethod("GET"))
		                .respond(response()
				                .withStatusCode(429)
				                .withHeader("content-type", "application/json; charset=utf-8")
				                .withHeader("reason-phrase", "Mocked")
				                .withBody(responseBody));

		Personal personal = Feign.builder()
		                         .client(new Http2Client())
		                         .decoder(new GsonDecoder())
		                         .target(Personal.class, String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()));

		// when
		FeignException exception = assertThrows(FeignException.TooManyRequests.class, personal::getClientInfo);

		// then
		mockServerClient.verify(request("/personal/client-info").withMethod("GET"));

		assertAll("API server returns error",
				() -> assertEquals(429, exception.status(), "status"),
				() -> assertThat("message", exception.getMessage(), matchesPattern(messagePattern)));
	}
}
