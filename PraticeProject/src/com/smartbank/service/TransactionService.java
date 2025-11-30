package com.smartbank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.smartbank.model.Transaction;
import com.smartbank.model.TransactionType;

public class TransactionService {
	public final List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());

	public TransactionService() {
	}

	/**
	 * Initialize transactions from an existing list (used by persistence restore)
	 */
	public void initializeFrom(List<Transaction> existing) {
		transactions.clear();

		if (existing != null)
			transactions.addAll(existing);
	}
							
	public List<Transaction> getAllTransactions() {
		return transactions;
	}

	public void recordDeposit(int account, double amount) {
		transactions.add(new Transaction(null, account, amount, TransactionType.DEPOSIT, "Deposit"));
	}

	public void recordWithDraw(int account, double amount) {
		transactions.add(new Transaction(account, null, amount, TransactionType.WITHDRAW, "Withdrawn"));
	}

	public void recordtrasfer(int from, int to, double amount) {
		transactions.add(new Transaction(from, to, amount, TransactionType.TRANSFER, "Trasfer"));
	}

	public List<Transaction> getLastNForAccount(int account, int n) {
		List<Transaction> result = new ArrayList<Transaction>();

		synchronized (transactions) {
			for (int i = transactions.size() - 1; i >= 0 && result.size() < n; i--) {
				Transaction t = transactions.get(i);
				if ((t.getFromAccount() != null && t.getFromAccount().equals(account))
						|| (t.getToAccount() != null && t.getToAccount().equals(account))) {
					result.add(t);
				}

			}

		}

		return result;
	}

}
