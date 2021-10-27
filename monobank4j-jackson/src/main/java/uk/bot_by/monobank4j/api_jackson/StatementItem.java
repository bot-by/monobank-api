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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigInteger;
import java.time.Instant;

/**
 * Statement item.
 *
 * @see <a href="https://api.monobank.ua/docs/#definition-StatementItems">Monobank API: транзакція</a>
 */
public class StatementItem {

	private String id;
	@JsonDeserialize(using = UnixTimeDeserializer.class)
	private Instant time;
	private String description;
	private String comment;
	@JsonProperty("mcc")
	private int categoryCode;
	@JsonProperty("originalMcc")
	private int originalCategoryCode;
	private BigInteger amount;
	private BigInteger operationAmount;
	private int currencyCode;
	private BigInteger commissionRate;
	private BigInteger cashbackAmount;
	private BigInteger balance;
	private boolean hold;
	private String receiptId;
	@JsonProperty("counterEdrpou")
	private String counterLegalIdentifier;
	@JsonProperty("counterIban")
	private String counterAccount;

	/**
	 * Transaction identifier.
	 *
	 * @return alphanumeric identifier, some typographic marks can be used: underscore, hyphen
	 */
	public String getId() {
		return id;
	}

	public Instant getTime() {
		return time;
	}

	/**
	 * Transaction description.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Transaction comment.
	 *
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Merchant Category Code (ISO 18245).
	 *
	 * @return category code
	 */
	public int getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Original Merchant Category Code (ISO 18245).
	 *
	 * @return category code
	 */
	public int getOriginalCategoryCode() {
		return originalCategoryCode;
	}

	/**
	 * Amount in minor currency unit, uses currency of account.
	 *
	 * @return amount
	 */
	public BigInteger getAmount() {
		return amount;
	}

	/**
	 * Amount in minor currency unit, uses currency of operation.
	 *
	 * @return amount
	 */
	public BigInteger getOperationAmount() {
		return operationAmount;
	}

	/**
	 * The numeric code (ISO 4217) of currency.
	 *
	 * @return numeric currency code
	 */
	public int getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Commission in minor currency unit.
	 *
	 * @return commission
	 */
	public BigInteger getCommissionRate() {
		return commissionRate;
	}

	/**
	 * Cashback in minor currency unit.
	 *
	 * @return Cashback amount
	 */
	public BigInteger getCashbackAmount() {
		return cashbackAmount;
	}

	/**
	 * Account balance in minor currency unit.
	 *
	 * @return account balance
	 */
	public BigInteger getBalance() {
		return balance;
	}

	/**
	 * Is it hold?
	 *
	 * @return true if it is hold.
	 */
	public boolean isHold() {
		return hold;
	}

	/**
	 * Receipt number.
	 * <p>
	 * Receipt number for <a href="https://check.gov.ua/">check.gov.ua</a>
	 *
	 * @return alphanumeric identifier with hyphen delimiter
	 */
	public String getReceiptId() {
		return receiptId;
	}

	/**
	 * Legal identifier of counterparty, РНОКПП or ЄДРПОУ.
	 *
	 * @return legal identifier
	 */
	public String getCounterLegalIdentifier() {
		return counterLegalIdentifier;
	}

	/**
	 * Account number (IBAN) of counterparty.
	 *
	 * @return account number
	 */
	public String getCounterAccount() {
		return counterAccount;
	}

}
