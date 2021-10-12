package uk.bot_by.monobank4j.api_jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UnixTimeSerializerTest {

	@Mock
	private JsonGenerator generator;
	@Mock
	private SerializerProvider provider;

	private UnixTimeSerializer serializer;

	@BeforeEach
	public void setUp() {
		serializer = new UnixTimeSerializer();
	}

	@DisplayName("Write timestamp")
	@Test
	public void serialize() throws IOException {
		// given
		Instant timestamp = Instant.ofEpochSecond(1628370606);

		// when
		serializer.serialize(timestamp, generator, provider);

		// then
		verify(generator).writeNumber(1628370606L);
	}

	@DisplayName("Null value")
	@Test
	public void nullValue() throws IOException {
		// when
		serializer.serialize(null, generator, provider);

		// then
		verify(generator, never()).writeNumber(anyLong());
		verify(generator).writeNull();
	}

}
