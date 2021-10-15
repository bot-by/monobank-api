package uk.bot_by.monobank4j.util;

import feign.Param.Expander;

import java.net.URL;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class WebhookLocatorExpander implements Expander {

	@Override
	public String expand(Object value) {
		if (nonNull(value)) {
			if (value instanceof URL) {
				return ((URL) value).toExternalForm();
			}
			throw new IllegalArgumentException(format("%s is not a type supported by this expander", value.getClass()));
		}
		return "";
	}

}
