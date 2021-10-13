package uk.bot_by.monobank4j.api_jackson_jr;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.time.Instant;

public class Statement {

	private String id;
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
	private int commissionRate;
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

	public void setId(String id) {
		this.id = id;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public int getOriginalCategoryCode() {
		return originalCategoryCode;
	}

	public void setOriginalCategoryCode(int originalCategoryCode) {
		this.originalCategoryCode = originalCategoryCode;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public BigInteger getOperationAmount() {
		return operationAmount;
	}

	public void setOperationAmount(BigInteger operationAmount) {
		this.operationAmount = operationAmount;
	}

	public int getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(int commissionRate) {
		this.commissionRate = commissionRate;
	}

	public BigInteger getCashbackAmount() {
		return cashbackAmount;
	}

	public void setCashbackAmount(BigInteger cashbackAmount) {
		this.cashbackAmount = cashbackAmount;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getCounterLegalIdentifier() {
		return counterLegalIdentifier;
	}

	public void setCounterLegalIdentifier(String counterLegalIdentifier) {
		this.counterLegalIdentifier = counterLegalIdentifier;
	}

	public String getCounterAccount() {
		return counterAccount;
	}

	public void setCounterAccount(String counterAccount) {
		this.counterAccount = counterAccount;
	}

}
