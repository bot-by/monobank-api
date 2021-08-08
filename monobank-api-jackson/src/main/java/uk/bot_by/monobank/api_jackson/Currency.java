package uk.bot_by.monobank.api_jackson;

import feign.RequestLine;

import java.util.List;

/**
 * Get currency rates. It is part of Monobank Public API.
 *
 * <h3>How to get currency rates</h3>
 * <p>
 * First instantiate an instance of Monobank API with
 * <a href="https://github.com/OpenFeign/feign">Feign</a>
 * <pre><code class="language-java">
 * currency = Feign.builder()
 *                 .client(new Http2Client())
 *                 .decoder(new JacksonDecoder())
 *                 .target(Currency.class, "https://api.monobank.ua/");
 * </code></pre>
 * <p>
 * Then just get currency rates
 * <pre><code class="language-java">
 * List&lt;CurrencyInfo&gt; currencyExchangeRates = currency.getRates();
 * </code></pre>
 *
 * @see <a href="https://api.monobank.ua/docs/#operation--bank-currency-get">Monobank API: отримання курсів валют</a>
 */
public interface Currency {

	/**
	 * Get a list of Monobank's exchange rates.
	 * <p>
	 * <strong>Important:</strong><br>
	 * the data are cached and updated not more than once every 5 minutes.
	 *
	 * @return list of currency rates
	 * @see CurrencyInfo
	 */
	@RequestLine("GET /bank/currency")
	List<CurrencyInfo> getRates();

}
