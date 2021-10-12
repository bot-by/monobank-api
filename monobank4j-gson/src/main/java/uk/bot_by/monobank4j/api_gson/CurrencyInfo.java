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

import com.google.gson.annotations.JsonAdapter;

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
 * @see <a href="https://api.monobank.ua/docs/#/definitions/CurrencyInfo">CurrencyInfo: перелік курсів</a>
 */
public class CurrencyInfo {

	private int currencyCodeA;
	private int currencyCodeB;
	@JsonAdapter(UnixTimeTypeAdapter.class)
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
