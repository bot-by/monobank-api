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
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.time.Instant;

public class Statement {

	private String id;
	@JsonAdapter(UnixTimeTypeAdapter.class)
	private Instant time;
	private String description;
	private String comment;
	@SerializedName("mcc")
	private int categoryCode;
	@SerializedName("originalMcc")
	private int originalCategoryCode;
	private BigInteger amount;
	private BigInteger operationAmount;
	private int currencyCode;
	private int commissionRate;
	private BigInteger cashbackAmount;
	private BigInteger balance;
	private boolean hold;
	private String receiptId;
	@SerializedName("counterEdrpou")
	private String counterLegalIdentifier;
	@SerializedName("counterIban")
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

	public int getCommissionRate() {
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
