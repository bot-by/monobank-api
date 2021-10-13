package uk.bot_by.monobank4j.util;

import feign.Param.Expander;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("fast")
class UnixTimeExpanderTest {

	private Expander expander;

	@BeforeEach
	void setUp() {
		expander = new UnixTimeExpander();
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
	public void happyPath() {
		// given
		Instant time = Instant.ofEpochSecond(1234567890);

		// when and then
		assertEquals("1234567890", expander.expand(time), "expand to unix time string");
	}

}
