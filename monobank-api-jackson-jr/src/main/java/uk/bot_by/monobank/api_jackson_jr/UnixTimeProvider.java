package uk.bot_by.monobank.api_jackson_jr;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.ob.api.ReaderWriterProvider;
import com.fasterxml.jackson.jr.ob.api.ValueReader;
import com.fasterxml.jackson.jr.ob.api.ValueWriter;
import com.fasterxml.jackson.jr.ob.impl.JSONReader;
import com.fasterxml.jackson.jr.ob.impl.JSONWriter;

import java.io.IOException;
import java.time.Instant;

/**
 * The Jackson Jr.
 * <a href="https://cowtowncoder.medium.com/jackson-2-10-jackson-jr-improvements-9eb5bb7b35f">provider</a>
 * converts <a href="https://www.unixtimestamp.com/">Unix time</a> to {@link Instant} and vice versa.
 */
public class UnixTimeProvider extends ReaderWriterProvider {

	@Override
	public ValueReader findValueReader(JSONReader context, Class<?> type) {
		return (Instant.class == type) ? new UnixTimeValueReader() : super.findValueReader(context, type);
	}

	@Override
	public ValueWriter findValueWriter(JSONWriter context, Class<?> type) {
		return (Instant.class == type) ? new UnixTimeValueWriter() : super.findValueWriter(context, type);
	}

	public static class UnixTimeValueReader extends ValueReader {

		protected UnixTimeValueReader() {
			super(Instant.class);
		}

		@Override
		public Object read(JSONReader reader, JsonParser parser) throws IOException {
			return Instant.ofEpochSecond(parser.getLongValue());
		}

	}

	public static class UnixTimeValueWriter implements ValueWriter {

		@Override
		public void writeValue(JSONWriter writer, JsonGenerator generator, Object value) throws IOException {
			writer.writeValue(((Instant) value).getEpochSecond());
		}

		@Override
		public Class<?> valueType() {
			return Instant.class;
		}

	}

}
