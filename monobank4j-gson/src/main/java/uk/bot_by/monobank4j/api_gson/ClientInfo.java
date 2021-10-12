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

	public String getName() {
		return name;
	}

	public String getWebHookUrl() {
		return webHookUrl;
	}

	public String getPermissions() {
		return permissions;
	}

	public List<Account> getAccounts() {
		return accounts;
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

		public String getSendId() {
			return sendId;
		}

		public Integer getCurrencyCode() {
			return currencyCode;
		}

		public String getCashbackType() {
			return cashbackType;
		}

		public BigInteger getBalance() {
			return balance;
		}

		public BigInteger getCreditLimit() {
			return creditLimit;
		}

		public List<String> getMaskedPan() {
			return maskedPan;
		}

		public String getType() {
			return type;
		}

		public String getIban() {
			return iban;
		}

	}

}
