package uk.bot_by.monobank4j.api_json;

import feign.Feign;
import feign.Response;
import feign.json.JsonDecoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.RequestHeaders;
import feign.mock.RequestKey;
import org.json.JSONArray;
import org.json.JSONObject;
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

import static feign.mock.HttpMethod.GET;
import static feign.mock.HttpMethod.POST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class PersonalTest {

	private static JsonDecoder decoder;
	private static RequestKey clientInfoKey;
	private static RequestKey statementKey;
	private static RequestKey webhookKey;

	private Personal personal;
	private MockClient mockClient;

	@BeforeAll
	public static void setUpClass() {
		decoder = new JsonDecoder();
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
		JSONObject clientInfo = personal.getClientInfo();

		// then
		mockClient.verifyOne(GET, "/personal/client-info");

		assertAll("Client information",
				() -> assertNotNull(clientInfo, "client information"),
				() -> assertEquals("vF8Sml8ukr", clientInfo.getString("clientId"), "client id"),
				() -> assertEquals("Огієнко Мар'ян", clientInfo.getString("name"), "name"),
				() -> assertTrue(clientInfo.getString("webHookUrl").isEmpty(), "webhook"),
				() -> assertEquals("xyz", clientInfo.getString("permissions"), "permissions"),
				() -> assertEquals(2, clientInfo.getJSONArray("accounts").length(), "accounts"));

		JSONObject black = clientInfo.getJSONArray("accounts").getJSONObject(0);
		JSONObject business = clientInfo.getJSONArray("accounts").getJSONObject(1);

		assertAll("Black",
				() -> assertEquals("gyY61faILGJUgkWneaO4oL", black.getString("id"), "id"),
				() -> assertEquals("UrggEjCAz0", black.getString("sendId"), "send id"),
				() -> assertEquals(980, black.getInt("currencyCode"), "currency code"),
				() -> assertEquals("UAH", black.getString("cashbackType"), "cashback"),
				() -> assertEquals(BigInteger.valueOf(2000000), black.getBigInteger("balance"), "balance"),
				() -> assertEquals(BigInteger.valueOf(1000000), black.getBigInteger("creditLimit"), "credit limit"),
				() -> assertEquals("black", black.getString("type"), "type"),
				() -> assertEquals("UA203975964178795464987195612", black.getString("iban"), "iban"),
				() -> assertEquals(1, black.getJSONArray("maskedPan").length(), "PANs"),
				() -> assertEquals("557841******6454", black.getJSONArray("maskedPan").getString(0), "masked PAN"));
		assertAll("FOP",
				() -> assertEquals("huv6fBmk6PCk14M56WJAWs", business.getString("id"), "id"),
				() -> assertTrue(business.getString("sendId").isEmpty(), "send id"),
				() -> assertEquals(980, business.getInt("currencyCode"), "currency code"),
				() -> assertTrue(business.getString("cashbackType").isEmpty(), "cashback"),
				() -> assertEquals(BigInteger.ZERO, business.getBigInteger("balance"), "balance"),
				() -> assertEquals(BigInteger.ZERO, business.getBigInteger("creditLimit"), "credit limit"),
				() -> assertEquals("fop", business.getString("type"), "type"),
				() -> assertEquals("UA263985562455143666954374231", business.getString("iban"), "iban"),
				() -> assertEquals(0, business.getJSONArray("maskedPan").length(), "PANs"));
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
		JSONArray statementItems = personal.getStatement("0", from, to);

		// then
		mockClient.verifyOne(GET, "/personal/statement/0/1633035329/1633355389");
		assertEquals(1, statementItems.length(), "statement items");

		JSONObject statementItem = statementItems.getJSONObject(0);

		assertAll("Statement",
				() -> assertEquals("20zs7EZAmWwAe8va", statementItem.getString("id"), "id"),
				() -> assertEquals(1633355174, statementItem.getInt("time"), "time"),
				() -> assertEquals("Харкiвський Метрополiтен", statementItem.getString("description"), "description"),
				() -> assertEquals("червона лінія", statementItem.getString("comment"), "comment"),
				() -> assertEquals(4111, statementItem.getInt("mcc"), "mcc"),
				() -> assertEquals(1114, statementItem.getInt("originalMcc"), "originalMcc"),
				() -> assertEquals(BigInteger.valueOf(-800), statementItem.getBigInteger("amount"), "amount"),
				() -> assertEquals(BigInteger.valueOf(-800), statementItem.getBigInteger("operationAmount"), "operationAmount"),
				() -> assertEquals(980, statementItem.getInt("currencyCode"), "currencyCode"),
				() -> assertEquals(BigInteger.ZERO, statementItem.getBigInteger("commissionRate"), "commissionRate"),
				() -> assertEquals(BigInteger.valueOf(400), statementItem.getBigInteger("cashbackAmount"), "cashbackAmount"),
				() -> assertEquals(BigInteger.valueOf(999200), statementItem.getBigInteger("balance"), "balance"),
				() -> assertFalse(statementItem.getBoolean("hold"), "hold"),
				() -> assertEquals("YQEF-VB2M-ZY1V-3J3O", statementItem.getString("receiptId"), "receiptId"),
				() -> assertEquals("4224027620", statementItem.getString("counterEdrpou"), "counterEdrpou"),
				() -> assertEquals("UA711353474611593256779295886", statementItem.getString("counterIban"), "counterIban"));
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
