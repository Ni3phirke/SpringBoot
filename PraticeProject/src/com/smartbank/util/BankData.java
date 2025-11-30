package com.smartbank.util;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import com.smartbank.model.Account;
import com.smartbank.model.Transaction;

public class BankData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final Map<Integer,Account> accounts;
							   	
	private final List<Transaction> transaction;
	private final int nextAccountSeq;
	
	public BankData(Map<Integer, Account> accounts, List<Transaction> transaction, int nextAccountSeq) {
		super();
		this.accounts = accounts;
		this.transaction = transaction;
		this.nextAccountSeq = nextAccountSeq;
	}

	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public int getNextAccountSeq() {
		return nextAccountSeq;
	}
	
	

	
}
