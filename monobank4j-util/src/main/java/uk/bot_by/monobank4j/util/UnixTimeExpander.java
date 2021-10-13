/*
 * Copyright 2021 Witalij Berdinskich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.bot_by.monobank4j.util;


import feign.Param.Expander;

import java.time.Instant;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class UnixTimeExpander implements Expander {

	@Override
	public String expand(Object value) {
		if (nonNull(value)) {
			if (value instanceof Instant) {
				return Long.toString(((Instant) value).getEpochSecond());
			}
			throw new IllegalArgumentException(format("%s is not a type supported by this expander", value.getClass()));
		}
		return "";
	}

}
