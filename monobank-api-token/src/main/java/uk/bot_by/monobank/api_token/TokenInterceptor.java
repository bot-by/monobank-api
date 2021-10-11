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
package uk.bot_by.monobank.api_token;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * The interceptor helps to add Monobank Personal API token to a request.
 *
 * @see <a href="https://api.monobank.ua/docs/#operation--personal-client-info-get">Monobank API: тoken для особистого доступу до API</a>
 */
public class TokenInterceptor implements RequestInterceptor {

	private static final String TOKEN = "X-Token";

	private final String token;

	public TokenInterceptor(@NotNull String token) {
		this.token = token;
	}

	@Override
	public void apply(RequestTemplate template) {
		if (!(template.headers().containsKey(TOKEN))) {
			template.header(TOKEN, token);
		}
	}

}
