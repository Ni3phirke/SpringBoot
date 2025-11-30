package com.smartbank.model;

import java.time.LocalDateTime;

import com.smartbank.exception.InsufficientBalanceException;

public class SavingsAccount extends Account {

	private final static double MIN_BALANCE = 500.0;

	public SavingsAccount(int accountNumber, String customerName, double balace) {
		super(accountNumber, customerName, balace);
	}

	@Override
	public void withdraw(double amt) throws InsufficientBalanceException {

		if (amt <= 0)
			throw new IllegalArgumentException("Amount must be positive");

		if (this.balance - amt < MIN_BALANCE)
			throw new InsufficientBalanceException("Withdrawal would breach minimum balance of " + MIN_BALANCE);
		
		  this.balance -= amt;
	}

}
