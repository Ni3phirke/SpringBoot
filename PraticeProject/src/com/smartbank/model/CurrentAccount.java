package com.smartbank.model;

import java.time.LocalDateTime;

import com.smartbank.exception.InsufficientBalanceException;

public class CurrentAccount extends Account {



	private static final double OVERDRAFT_LIMIT = -10000;
	
	public CurrentAccount(int accountNumber, String customerName, double balace) {
		super(accountNumber, customerName, balace);
	}

	@Override
	public void withdraw(double amt) throws InsufficientBalanceException {

		if (amt <= 0)
			throw new IllegalArgumentException("Amount must be positive");

		if (this.balance - amt < OVERDRAFT_LIMIT) {
			throw new InsufficientBalanceException("Overdrafft Limit excited");
		}
		  this.balance -=  amt;

	}

}
