package uk.bot_by.monobank4j.api_jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UnixTimeDeserializerTest {

	@Mock
	private DeserializationContext context;
	@Mock
	private JsonParser parser;

	private UnixTimeDeserializer deserializer;

	@BeforeEach
	public void setUp() {
		deserializer = new UnixTimeDeserializer();
	}

	@DisplayName("Read timestamp")
	@Test
	public void deserialize() throws IOException {
		// given
		when(parser.getLongValue()).thenReturn(1628370606L);

		// when
		Instant timestamp = deserializer.deserialize(parser, context);

		// then
		verify(parser).getLongValue();

		assertEquals(1628370606, timestamp.getEpochSecond(), "timestamp");
	}

}
