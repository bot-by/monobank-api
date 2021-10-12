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
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.net.URL;
import java.time.Instant;

public interface Personal {

	@RequestLine("GET /personal/client-info")
	ClientInfo getClientInfo();

	@RequestLine("GET /personal/statement/{account}/{from}/{to}")
	Response getStatements(@Param("account") String account, @Param("from") Instant from, @Param("to") Instant to);

	@RequestLine("POST /personal/webhook")
	@Headers({"Content-Type: application/json"})
	@Body("%7B \"webHookUrl\": \"{webhookLocator}\" %7D")
	Response setWebhook(@Param("webhookLocator") URL webhookLocator);

}
