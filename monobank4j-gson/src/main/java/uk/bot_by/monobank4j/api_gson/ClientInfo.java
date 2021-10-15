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

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.net.URL;
import java.util.List;

/**
 * Client information:
 * <ul>
 *     <li>name,</li>
 *     <li>permission,</li>
 *     <li>accounts,</li>
 *     <li>webhook locator.</li>
 * </ul>
 *
 * @see <a href="https://api.monobank.ua/docs/#definition-UserInfo">Опис клієнта та його рахунків</a>
 */
public class ClientInfo {

	private String clientId;
	private String name;
	@SerializedName("webHookUrl")
	private String webhookLocator;
	private String permissions;
	private List<Account> accounts;

	/**
	 * Client identifier.
	 *
	 * @return alphanumeric identifier, some typographic marks can be used: underscore, hyphen.
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Surname and given name, usually in Ukrainian.
	 *
	 * @return surname and given name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Webhook locator.
	 *
	 * @return URL as string
	 * @see Personal#setWebhook(URL)
	 */
	public String getWebhookLocator() {
		return webhookLocator;
	}

	/**
	 * Client's permissions:
	 * <ul>
	 *     <li><strong>s</strong> - statement,</li>
	 *     <li><strong>p</strong> - personal data,</li>
	 *     <li><strong>f</strong> - <strong>?</strong></li>
	 * </ul>
	 *
	 * @return permissions
	 */
	public String getPermissions() {
		return permissions;
	}

	/**
	 * Client's accounts.
	 *
	 * @return list of accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Client account.
	 */
	public static class Account {

		private String id;
		private String sendId;
		private Integer currencyCode;
		private String cashbackType;
		private BigInteger balance;
		private BigInteger creditLimit;
		@SerializedName("maskedPan")
		private List<String> maskedPrimaryAccounts;
		private String type;
		@SerializedName("iban")
		private String account;

		/**
		 * Account identifier.
		 *
		 * @return alphanumeric identifier, some typographic marks can be used: underscore, hyphen
		 */
		public String getId() {
			return id;
		}

		/**
		 * Account identifier is used to make a money transfer web-link.
		 *
		 * @return alphanumeric identifier, some typographic marks can be used: underscore, hyphen
		 * @see <a href="https://send.monobank.ua/3MxmcjWwoK?f=enabled&t=%D0%BD%D0%B0+%D1%80%D0%BE%D0%B7%D0%B2%D0%B8%D1%82%D0%BE%D0%BA+java+wrapper&a=100">My own link, just for example how it works</a>
		 */
		public String getSendId() {
			return sendId;
		}

		/**
		 * The numeric code (ISO 4217) of currency.
		 *
		 * @return numeric currency code
		 */
		public Integer getCurrencyCode() {
			return currencyCode;
		}

		/**
		 * Cashback type.
		 * <p>
		 * Available values: <em>UAH</em>, <em>Miles</em> and empty string.
		 *
		 * @return cashback type
		 */
		public String getCashbackType() {
			return cashbackType;
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
		 * Credit limit in minor currency unit.
		 *
		 * @return account balance
		 */
		public BigInteger getCreditLimit() {
			return creditLimit;
		}

		/**
		 * List of masked PAN.
		 *
		 * @return PAN list
		 */
		public List<String> getMaskedPrimaryAccounts() {
			return maskedPrimaryAccounts;
		}

		/**
		 * Account type.
		 * <p>
		 * Available values: black, white, platinum, iron, fop, yellow.
		 *
		 * @return account type
		 */
		public String getType() {
			return type;
		}

		/**
		 * Account number (IBAN).
		 *
		 * @return account number.
		 */
		public String getAccount() {
			return account;
		}

	}

}
