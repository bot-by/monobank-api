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
package uk.bot_by.monobank4j.api_jackson_jr;

import java.math.BigInteger;
import java.util.List;

public class ClientInfo {

	private String clientId;
	private String name;
	private String webHookUrl;
	private String permissions;
	private List<Account> accounts;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebHookUrl() {
		return webHookUrl;
	}

	public void setWebHookUrl(String webHookUrl) {
		this.webHookUrl = webHookUrl;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public static class Account {

		private String id;
		private String sendId;
		private Integer currencyCode;
		private String cashbackType;
		private BigInteger balance;
		private BigInteger creditLimit;
		private List<String> maskedPan;
		private String type;
		private String iban;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSendId() {
			return sendId;
		}

		public void setSendId(String sendId) {
			this.sendId = sendId;
		}

		public Integer getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}

		public String getCashbackType() {
			return cashbackType;
		}

		public void setCashbackType(String cashbackType) {
			this.cashbackType = cashbackType;
		}

		public BigInteger getBalance() {
			return balance;
		}

		public void setBalance(BigInteger balance) {
			this.balance = balance;
		}

		public BigInteger getCreditLimit() {
			return creditLimit;
		}

		public void setCreditLimit(BigInteger creditLimit) {
			this.creditLimit = creditLimit;
		}

		public List<String> getMaskedPan() {
			return maskedPan;
		}

		public void setMaskedPan(List<String> maskedPan) {
			this.maskedPan = maskedPan;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getIban() {
			return iban;
		}

		public void setIban(String iban) {
			this.iban = iban;
		}

	}

}
