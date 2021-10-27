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
package uk.bot_by.monobank4j.api_jackson;

import feign.Body;
import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import feign.jackson.JacksonDecoder;
import uk.bot_by.monobank4j.util.TokenInterceptor;
import uk.bot_by.monobank4j.util.UnixTimeExpander;

import java.net.URL;
import java.time.Instant;
import java.util.List;

import static uk.bot_by.monobank4j.util.MonobankConstants.ACCOUNT;
import static uk.bot_by.monobank4j.util.MonobankConstants.API_MONOBANK_UA;
import static uk.bot_by.monobank4j.util.MonobankConstants.CONTENT_TYPE_APPLICATION_JSON;
import static uk.bot_by.monobank4j.util.MonobankConstants.FROM;
import static uk.bot_by.monobank4j.util.MonobankConstants.PERSONAL_CLIENT_INFO;
import static uk.bot_by.monobank4j.util.MonobankConstants.PERSONAL_STATEMENT;
import static uk.bot_by.monobank4j.util.MonobankConstants.PERSONAL_WEBHOOK;
import static uk.bot_by.monobank4j.util.MonobankConstants.TO;
import static uk.bot_by.monobank4j.util.MonobankConstants.WEBHOOK_BODY;
import static uk.bot_by.monobank4j.util.MonobankConstants.WEBHOOK_LOCATOR;

/**
 * Get personal statement, client information and set webhook.
 *
 * @see <a href="https://api.monobank.ua/docs/#tag----------------------------">Monobank API: клієнтські персональні дані</a>
 */
public interface Personal {

	/**
	 * Get an instance of currency Monobank Personal API.
	 * <p>
	 * Monobank Personal API requires an access token. If you are client of Monobank, you can get your own token on
	 * <a href="https://api.monobank.ua/">api.monobank.ua</a>.
	 *
	 * @param token personal token
	 * @return a personal API instance
	 */
	static Personal getInstance(String token) {
		return Feign.builder()
		            .decoder(new JacksonDecoder())
		            .requestInterceptor(new TokenInterceptor(token))
		            .target(Personal.class, API_MONOBANK_UA);
	}

	/**
	 * Get client information.
	 *
	 * @return client information
	 * @see <a href="https://api.monobank.ua/docs/#operation--personal-client-info-get">Monobank API: інформація про клієнта</a>
	 */
	@RequestLine(PERSONAL_CLIENT_INFO)
	ClientInfo getClientInfo();

	/**
	 * Get a statement.
	 * <p>
	 * There is some restrictions from API side:
	 * <ol>
	 *     <li>maximum statement period is 31 day + 1 hour,</li>
	 *     <li>you should not make a request more than one time a minute</li>
	 * </ol>
	 *
	 * @param account account id or 0 (for main account)
	 * @param from    start time of period
	 * @param to      finish time of period, if it is <code>null</code>, current time is used
	 * @return list of operations
	 * @see <a href="https://api.monobank.ua/docs/#operation--personal-statement--account---from---to--get">Monobank API: виписка</a>
	 */
	@RequestLine(PERSONAL_STATEMENT)
	List<StatementItem> getStatement(@Param(ACCOUNT) String account,
	                                 @Param(value = FROM, expander = UnixTimeExpander.class) Instant from,
	                                 @Param(value = TO, expander = UnixTimeExpander.class) Instant to);

	/**
	 * Set a webhook.
	 * <p>
	 * Monobank API uses a webhook to send updates when a client does any transactions on account.
	 * <pre><code class="language-json">{type:"StatementItem", data:{account:"...", statementItem:{#StatementItem}}}</code></pre>
	 *
	 * @param webhookLocator webhook locator, use <code>null</code> to remove webhook
	 * @return API response
	 * @see <a href="https://api.monobank.ua/docs/#operation--personal-webhook-post">Monobank API: встановляння WebHook</a>
	 * @see StatementItem
	 */
	@RequestLine(PERSONAL_WEBHOOK)
	@Headers({CONTENT_TYPE_APPLICATION_JSON})
	@Body(WEBHOOK_BODY)
	Response setWebhook(@Param(WEBHOOK_LOCATOR) URL webhookLocator);

}
