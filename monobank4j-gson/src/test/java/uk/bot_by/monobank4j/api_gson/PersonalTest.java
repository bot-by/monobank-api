package uk.bot_by.monobank4j.api_gson;

import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.RequestHeaders;
import feign.mock.RequestKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static feign.mock.HttpMethod.GET;
import static feign.mock.HttpMethod.POST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class PersonalTest {

	private static GsonDecoder decoder;
	private static RequestKey clientInfoKey;
	private static RequestKey webhookKey;

	private Personal personal;
	private MockClient mockClient;

	@BeforeAll
	public static void setUpClass() {
		decoder = new GsonDecoder();
		clientInfoKey = RequestKey.builder(GET, "/personal/client-info").build();
		webhookKey = RequestKey.builder(POST, "/personal/webhook")
		                       .body("{ \"webHookUrl\": \"https://mono.example.com/statements\" }")
		                       .charset(UTF_8)
		                       .headers(RequestHeaders.builder()
		                                              .add("Content-Length", "55")
		                                              .add("Content-Type", "application/json")
		                                              .build())
		                       .build();
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
		ClientInfo clientInfo = personal.getClientInfo();

		// then
		mockClient.verifyOne(GET, "/personal/client-info");

		assertAll("Client information",
				() -> assertNotNull(clientInfo, "client information"),
				() -> assertEquals("vF8Sml8ukr", clientInfo.getClientId(), "client id"),
				() -> assertEquals("Огієнко Мар'ян", clientInfo.getName(), "name"),
				() -> assertTrue(clientInfo.getWebHookUrl().isEmpty(), "webhook"),
				() -> assertEquals("xyz", clientInfo.getPermissions(), "permissions"),
				() -> assertThat("accounts", clientInfo.getAccounts(), hasSize(2)));

		ClientInfo.Account black = clientInfo.getAccounts().iterator().next();

		assertAll("Black",
				() -> assertEquals("gyY61faILGJUgkWneaO4oL", black.getId(), "id"),
				() -> assertEquals("UrggEjCAz0", black.getSendId(), "send id"),
				() -> assertEquals(980, black.getCurrencyCode(), "currency code"),
				() -> assertEquals("UAH", black.getCashbackType(), "cashback"),
				() -> assertEquals(BigInteger.valueOf(2000000), black.getBalance(), "balance"),
				() -> assertEquals(BigInteger.valueOf(1000000), black.getCreditLimit(), "credit limit"),
				() -> assertEquals("black", black.getType(), "type"),
				() -> assertEquals("UA203975964178795464987195612", black.getIban(), "iban"),
				() -> assertThat("PANs", black.getMaskedPan(), hasSize(1)),
				() -> assertEquals("557841******6454", black.getMaskedPan().iterator().next(), "masked PAN"));
	}

	@Test
	void getStatements() {
	}

	@DisplayName("Set webhook")
	@Test
	void setWebhook() throws IOException {
		// given
		String responseBody = Files.readString(Path.of("src/test/resources/set_webhook/set_webhook.json"), UTF_8);
		URL webhookLocator = new URL("https://mono.example.com/statements");

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
