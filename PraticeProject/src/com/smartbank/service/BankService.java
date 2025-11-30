package com.smartbank.service;

import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import com.smartbank.exception.InsufficientBalanceException;
import com.smartbank.model.Account;
import com.smartbank.model.AccountType;
import com.smartbank.model.CurrentAccount;
import com.smartbank.model.SavingsAccount;
import com.smartbank.repository.BankRepository;

public class BankService {
	private final BankRepository repo;
	private final TransactionService txService;

	// Default constructor for quick instantiation (new repo+tx)
	public BankService() {
		this.repo = new BankRepository();
		this.txService = new TransactionService();
	}

	// Constructor injection (used for restored state)
	public BankService(BankRepository repo, TransactionService txService) {
		this.repo = repo;
		this.txService = txService;
	}

	public int createAccount(String name, AccountType type, double initialDeposit) {
		int accNo = repo.nextAccountNumber();
		Account account;
		if (type == AccountType.SAVINGS) {
			account = new SavingsAccount(accNo, name, accNo);
		} else {
			account = new CurrentAccount(accNo, name, accNo);
		}
		repo.save(account);
		txService.recordDeposit(accNo, initialDeposit);
		return accNo;

	}

	public Account getAccount(int accountNumber) throws AccountNotFoundException {
		Account a = repo.find(accountNumber);
		if (a == null)
				throw new AccountNotFoundException("Account not present : " + accountNumber);
		return a;
	}
	
	public synchronized void deposit(int accountNumber,double amount) throws AccountNotFoundException
	{
		Account acct=getAccount(accountNumber);
		acct.deposit(amount);
		txService.recordDeposit(accountNumber, amount);
	}
	
	
	
	
	public synchronized void withDrwan(int accountNumber,double amount) throws AccountNotFoundException,Exception {
		Account acct=getAccount(accountNumber);
		acct.withdraw(amount);
		txService.recordWithDraw(accountNumber, amount);
	}
	
	public void transfer(int fromAcc,int toAcc, double amount) throws AccountNotFoundException,Exception
	{
		synchronized (this) 
		{
			Account from=getAccount(fromAcc);
			Account to=getAccount(toAcc);
			from.withdraw(amount);
			to.deposit(amount);
			txService.recordtrasfer(fromAcc, toAcc, amount);
		}
	}
	
	public void printMiniStatement(int accountNumber,int n) throws AccountNotFoundException 
	{
		getAccount(accountNumber);
		txService.getLastNForAccount(accountNumber, n).forEach(System.out::println);
		
	}
	
	 // Persistence helper - build BankData snapshot
	public com.smartbank.util.BankData snapshotForPersistence()
	{
		 return new com.smartbank.util.BankData(repo.getAccountsMap(), txService.getAllTransactions(), repo.getNextSeqValue());
	}
	
	public void getAllaccountDetails() 
	{
		Map<Integer,Account>allAcount=repo.getAccountsMap();
		allAcount.forEach((key,value)->{
			System.out.println( key + " " +value.toString() );
		});
	}
	

}
