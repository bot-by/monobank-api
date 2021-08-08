package uk.bot_by.monobank.api_jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

/**
 * The Jackson deserializer converts <a href="https://www.unixtimestamp.com/">Unix time</a> to {@link Instant}.
 */
public class UnixTimeDeserializer extends JsonDeserializer<Instant> {

	@Override
	public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		return Instant.ofEpochSecond(jsonParser.getLongValue());
	}

}
