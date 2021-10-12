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
package uk.bot_by.monobank4j.api_gson;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.net.URL;
import java.time.Instant;

import static uk.bot_by.monobank4j.util.MonobankConstants.ACCOUNT;
import static uk.bot_by.monobank4j.util.MonobankConstants.CONTENT_TYPE_APPLICATION_JSON;
import static uk.bot_by.monobank4j.util.MonobankConstants.FROM;
import static uk.bot_by.monobank4j.util.MonobankConstants.PERSONAL_CLIENT_INFO;
import static uk.bot_by.monobank4j.util.MonobankConstants.PERSONAL_STATEMENT;
import static uk.bot_by.monobank4j.util.MonobankConstants.PERSONAL_WEBHOOK;
import static uk.bot_by.monobank4j.util.MonobankConstants.TO;
import static uk.bot_by.monobank4j.util.MonobankConstants.WEBHOOK_BODY;
import static uk.bot_by.monobank4j.util.MonobankConstants.WEBHOOK_LOCATOR;

public interface Personal {

	@RequestLine(PERSONAL_CLIENT_INFO)
	ClientInfo getClientInfo();

	@RequestLine(PERSONAL_STATEMENT)
	Response getStatements(@Param(ACCOUNT) String account, @Param(FROM) Instant from, @Param(TO) Instant to);

	@RequestLine(PERSONAL_WEBHOOK)
	@Headers({CONTENT_TYPE_APPLICATION_JSON})
	@Body(WEBHOOK_BODY)
	Response setWebhook(@Param(WEBHOOK_LOCATOR) URL webhookLocator);

}
