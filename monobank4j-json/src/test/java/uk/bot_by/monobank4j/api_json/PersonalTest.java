package uk.bot_by.monobank4j.api_json;

import feign.Feign;
import feign.json.JsonDecoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.RequestKey;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

import static feign.mock.HttpMethod.GET;
import static feign.mock.HttpMethod.POST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class PersonalTest {

	private static JsonDecoder decoder;
	private static RequestKey clientInfoKey;
	private static RequestKey webhookKey;

	private Personal personal;
	private MockClient mockClient;

	@BeforeAll
	public static void setUpClass() {
		decoder = new JsonDecoder();
		clientInfoKey = RequestKey.builder(GET, "/personal/client-info").build();
		webhookKey = RequestKey.builder(POST, "/personal/webhook").build();
	}

	@BeforeEach
	void setUp() {
	}

	@DisplayName("Get client information")
	@Test
	void getClientInfo() throws IOException {
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
	}

	@Test
	void getStatements() {
	}

	@Test
	void setWebhook() {
	}

}
