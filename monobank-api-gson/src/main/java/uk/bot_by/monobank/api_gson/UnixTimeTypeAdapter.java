package uk.bot_by.monobank.api_gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;

import static java.util.Objects.isNull;

/**
 * The
 * <a href="https://github.com/google/gson/blob/master/UserGuide.md#TOC-Custom-Serialization-and-Deserialization">Gson adapter</a>
 * converts <a href="https://www.unixtimestamp.com/">Unix time</a> to {@link Instant} and vice versa.
 */
public class UnixTimeTypeAdapter extends TypeAdapter<Instant> {

	@Override
	public void write(JsonWriter out, Instant value) throws IOException {
		if (isNull(value)) {
			out.nullValue();
		} else {
			out.value(value.getEpochSecond());
		}
	}

	@Override
	public Instant read(JsonReader in) throws IOException {
		if (JsonToken.NULL == in.peek()) {
			in.nextNull();
			return null;
		} else {
			return Instant.ofEpochSecond(in.nextLong());
		}
	}

}
