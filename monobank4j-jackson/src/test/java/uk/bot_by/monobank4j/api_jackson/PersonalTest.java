package uk.bot_by.monobank4j.api_jackson;

import feign.Feign;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.RequestHeaders;
import feign.mock.RequestKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import static feign.mock.HttpMethod.GET;
import static feign.mock.HttpMethod.POST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class PersonalTest {

	private static JacksonDecoder decoder;
	private static RequestKey clientInfoKey;
	private static RequestKey statementKey;
	private static RequestKey webhookKey;

	private Personal personal;
	private MockClient mockClient;

	@BeforeAll
	public static void setUpClass() {
		decoder = new JacksonDecoder();
		clientInfoKey = RequestKey.builder(GET, "/personal/client-info").build();
		statementKey = RequestKey.builder(GET, "/personal/statement/0/1633035329/1633355389").build();
		webhookKey = RequestKey.builder(POST, "/personal/webhook")
		                       .body("{ \"webHookUrl\": \"https://mono.example.com/statement\" }")
		                       .charset(UTF_8)
		                       .headers(RequestHeaders.builder()
		                                              .add("Content-Length", "54")
		                                              .add("Content-Type", "application/json")
		                                              .build())
		                       .build();
	}

	@DisplayName("Get client information")
	@Test
	public void getClientInfo() throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/client-info/client-info.json"), UTF_8);

		mockClient = new MockClient().ok(clientInfoKey, responseBody);
		personal = Feign.builder()
		                .decoder(decoder)
		                .client(mockClient)
		                .target(new MockTarget<>(Personal.class));

		// when
		ClientInfo clientInfo = personal.getClientInfo();

		// then
		mockClient.verifyOne(GET, "/personal/client-info");

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

	@DisplayName("Get statements")
	@Test
	public void getStatements() throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/statement/statement.json"), UTF_8);
		Instant from = Instant.ofEpochSecond(1633035329);
		Instant to = Instant.ofEpochSecond(1633355389);

		mockClient = new MockClient().ok(statementKey, responseBody);
		personal = Feign.builder()
		                .decoder(decoder)
		                .client(mockClient)
		                .target(new MockTarget<>(Personal.class));

		// when
		List<StatementItem> statementItems = personal.getStatement("0", from, to);

		// then
		mockClient.verifyOne(GET, "/personal/statement/0/1633035329/1633355389");
		assertThat("statement items", statementItems, hasSize(1));

		StatementItem statementItem = statementItems.get(0);

		assertAll("Statement",
				() -> assertEquals("20zs7EZAmWwAe8va", statementItem.getId(), "id"),
				() -> assertEquals(1633355174, statementItem.getTime().getEpochSecond(), "time"),
				() -> assertEquals("Харкiвський Метрополiтен", statementItem.getDescription(), "description"),
				() -> assertEquals("червона лінія", statementItem.getComment(), "comment"),
				() -> assertEquals(4111, statementItem.getCategoryCode(), "mcc"),
				() -> assertEquals(1114, statementItem.getOriginalCategoryCode(), "originalMcc"),
				() -> assertEquals(BigInteger.valueOf(-800), statementItem.getAmount(), "amount"),
				() -> assertEquals(BigInteger.valueOf(-800), statementItem.getOperationAmount(), "operationAmount"),
				() -> assertEquals(980, statementItem.getCurrencyCode(), "currencyCode"),
				() -> assertEquals(BigInteger.ZERO, statementItem.getCommissionRate(), "commissionRate"),
				() -> assertEquals(BigInteger.valueOf(400), statementItem.getCashbackAmount(), "cashbackAmount"),
				() -> assertEquals(BigInteger.valueOf(999200), statementItem.getBalance(), "balance"),
				() -> assertFalse(statementItem.isHold(), "hold"),
				() -> assertEquals("YQEF-VB2M-ZY1V-3J3O", statementItem.getReceiptId(), "receiptId"),
				() -> assertEquals("4224027620", statementItem.getCounterLegalIdentifier(), "counterEdrpou"),
				() -> assertEquals("UA711353474611593256779295886", statementItem.getCounterAccount(), "counterIban"));
	}

	@DisplayName("Set webhook")
	@Test
	public void setWebhook() throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/set_webhook/set_webhook.json"), UTF_8);
		URL webhookLocator = new URL("https://mono.example.com/statement");

		mockClient = new MockClient().ok(webhookKey, responseBody);
		personal = Feign.builder()
		                .decoder(decoder)
		                .client(mockClient)
		                .target(new MockTarget<>(Personal.class));

		// when
		Response response = personal.setWebhook(webhookLocator);

		// then
		assertEquals(200, response.status(), "OK");
	}

}
