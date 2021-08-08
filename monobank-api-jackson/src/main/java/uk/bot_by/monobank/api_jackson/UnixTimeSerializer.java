package uk.bot_by.monobank.api_jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

import static java.util.Objects.isNull;

/**
 * The Jackson serializer converts {@link Instant} to <a href="https://www.unixtimestamp.com/">Unix time</a>.
 */
public class UnixTimeSerializer extends JsonSerializer<Instant> {

	@Override
	public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (isNull(instant)) {
			jsonGenerator.writeNull();
		} else {
			jsonGenerator.writeNumber(instant.getEpochSecond());
		}
	}

}
