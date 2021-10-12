package uk.bot_by.monobank4j.api_jackson;

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
