package com.smartbank.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5947187723553029420L;
	protected final int accountNumber;
	protected final String customerName;
	protected double balance;
	protected final LocalDateTime createAt;

	public Account(int accountNumber, String customerName, double balance) {
		super();
		this.accountNumber = accountNumber;
		this.customerName = customerName;
		this.balance = balance;
		this.createAt = LocalDateTime.now();
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public double getBalace() {
		return balance;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public synchronized void deposit(double amt) 
	{
		if (amt <= 0)
			throw new IllegalArgumentException("Amount must be positive");

	}
	
	public abstract void withdraw(double amt) throws Exception;

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", customerName=" + customerName + ", balace=" + balance
				+ ", createAt=" + createAt + "]";
	}
	
	

}
