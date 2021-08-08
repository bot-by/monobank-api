package uk.bot_by.monobank.api_json;

import feign.RequestLine;
import org.json.JSONArray;

/**
 * Get currency rates. It is part of Monobank Public API.
 *
 * @see <a href="https://api.monobank.ua/docs/#operation--bank-currency-get">Monobank API: отримання курсів валют</a>
 */
public interface Currency {

	/**
	 * Get a list of Monobank's exchange rates.
	 * <p>
	 * <h3>How to get currency rates</h3>
	 * <p>
	 * First instantiate an instance of Monobank API with
	 * <a href="https://github.com/OpenFeign/feign">Feign</a>
	 * <pre><code class="language-java">
	 * currency = Feign.builder()
	 *                 .client(new Http2Client())
	 *                 .decoder(new JsonDecoder())
	 *                 .target(Currency.class, "https://api.monobank.ua/");
	 * </code></pre>
	 * <p>
	 * Then just get currency rates
	 * <pre><code class="language-java">
	 * JSONArray currencyExchangeRates = currency.getRates();
	 * </code></pre>
	 *
	 * @return list of currency rates
	 */
	@RequestLine("GET /bank/currency")
	JSONArray getRates();

}
