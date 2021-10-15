package uk.bot_by.monobank4j.util;

import feign.Param.Expander;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class WebhookLocatorExpanderTest {

	private Expander expander;

	@BeforeEach
	public void setUp() {
		expander = new WebhookLocatorExpander();
	}

	@DisplayName("Null value")
	@Test
	public void nullValue() {
		// when and then
		assertTrue(expander.expand(null).isEmpty(), "Null value");
	}

	@DisplayName("Class is not supported")
	@Test
	public void unsupportedClass() {
		// when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> expander.expand("test"), "Unsupported class");

		// then
		assertEquals("class java.lang.String is not a type supported by this expander", exception.getMessage(), "message");
	}

	@DisplayName("Happy path")
	@Test
	public void happyPath() throws MalformedURLException {
		// given
		URL webhookLocator = new URL("https://mono.example.com/statement");

		// when and then
		assertEquals("https://mono.example.com/statement", expander.expand(webhookLocator), "expand to URL string");
	}

}
