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
