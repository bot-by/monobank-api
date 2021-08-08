package uk.bot_by.monobank.api_jackson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Currency exchange rates.
 * <p>
 * It presents rates of the currency <em>A</em> to the currency <em>B</em>.
 * <p>
 * There are three exchange rates:
 * <ul>
 *     <li><strong>buy rate</strong> - Monobank buys,</li>
 *     <li><strong>sell rate</strong> - Monobank sells,</li>
 *     <li><strong>cross rate</strong> - cross rate by third currency, usually USD.</li>
 * </ul>
 * <p>
 * There are at least one of them:
 * <ul>
 *     <li>pair of fields <code>rateBuy</code> and <code>rateSell</code></li>
 *     <li>or <code>rateCross</code></li>
 * </ul>
 *
 * @see <a href="https://api.monobank.ua/docs/#/definitions/CurrencyInfo">CurrencyInfo: array of currency rates</a>
 */
public class CurrencyInfo {

	private int currencyCodeA;
	private int currencyCodeB;
	@JsonDeserialize(using = UnixTimeDeserializer.class)
	@JsonSerialize(using = UnixTimeSerializer.class)
	private Instant date;
	private BigDecimal rateBuy;
	private BigDecimal rateCross;
	private BigDecimal rateSell;

	/**
	 * The numeric code (ISO 4217) of currency <em>A</em>.
	 *
	 * @return numeric currency code
	 */
	public int getCurrencyCodeA() {
		return currencyCodeA;
	}

	/**
	 * The numeric code (ISO 4217) of currency <em>B</em>.
	 *
	 * @return numeric currency code
	 */
	public int getCurrencyCodeB() {
		return currencyCodeB;
	}

	public Instant getDate() {
		return date;
	}

	public BigDecimal getRateBuy() {
		return rateBuy;
	}

	public BigDecimal getRateCross() {
		return rateCross;
	}

	public BigDecimal getRateSell() {
		return rateSell;
	}

}
