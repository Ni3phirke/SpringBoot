package com.smartbank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3046724452327343069L;
	private final String id;
	private final Integer fromAccount;
	private final Integer toAccount;
	private final double amount;
	private final TransactionType type;
	private final LocalDateTime timestamp;
	private final String remarks;
						
	public Transaction(Integer fromAccount, Integer toAccount, double amount, TransactionType type, String remarks) {
		this.id = UUID.randomUUID().toString();
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.type = type;
		this.timestamp = LocalDateTime.now();
		this.remarks = remarks;
	}

	public String getId() {
		return id;
	}

	public Integer getFromAccount() {
		return fromAccount;
	}

	public Integer getToAccount() {
		return toAccount;
	}

	public double getAmount() {
		return amount;
	}

	public TransactionType getType() {
		return type;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getRemarks() {
		return remarks;
	}
	
	
	
	@Override
	public String toString() {
		return timestamp + " | " + type + " | from=" + fromAccount + " to=" + toAccount + " amount=" + amount
				+ " remarks=" + remarks;
	}
	
}
