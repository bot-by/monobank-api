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

	public String getId() {
		return id;
	}

	public Instant getTime() {
		return time;
	}

	public String getDescription() {
		return description;
	}

	public String getComment() {
		return comment;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public int getOriginalCategoryCode() {
		return originalCategoryCode;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public BigInteger getOperationAmount() {
		return operationAmount;
	}

	public int getCurrencyCode() {
		return currencyCode;
	}

	public BigInteger getCommissionRate() {
		return commissionRate;
	}

	public BigInteger getCashbackAmount() {
		return cashbackAmount;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public boolean isHold() {
		return hold;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public String getCounterLegalIdentifier() {
		return counterLegalIdentifier;
	}

	public String getCounterAccount() {
		return counterAccount;
	}

}
