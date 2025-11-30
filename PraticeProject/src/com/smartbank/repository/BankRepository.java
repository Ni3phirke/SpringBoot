package com.smartbank.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.smartbank.model.Account;

public class BankRepository {
	private final Map<Integer, Account> accounts = new ConcurrentHashMap<Integer, Account>();
	private final AtomicInteger accNoSeq = new AtomicInteger(1000);

	public BankRepository() {
	}

	public void initializeFrom(Map<Integer, Account> existingAccounts, int nextSeq) {
		if (existingAccounts != null) {
			accounts.clear();
			accounts.putAll(existingAccounts);
		}

		if (nextSeq > 0) {
			accNoSeq.set(nextSeq);
		}
	}

	public int nextAccountNumber() {
		return accNoSeq.getAndIncrement();
	}

	public int getNextSeqValue() {
		return accNoSeq.get();
	}

	public void save(Account account) {
		accounts.put(account.getAccountNumber(), account);
	}

	public Account find(int accountNumber) {
		return accounts.get(accountNumber);
	}

	public Collection<Account> findAll() {
		return accounts.values();
	}

	public Map<Integer, Account> getAccountsMap() {
		return accounts;
	}

}
