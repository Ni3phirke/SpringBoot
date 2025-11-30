package com.smartbank;

import java.io.File;
import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;

import com.smartbank.model.Account;
import com.smartbank.model.AccountType;
import com.smartbank.repository.BankRepository;
import com.smartbank.service.BankService;
import com.smartbank.service.TransactionService;
import com.smartbank.util.BankData;
import com.smartbank.util.PersistenceUtil;

public class Main {

	private static BankService bankservice;
	private static final File DATA_FILE = new File("data/bankdata.dat");

	public static void main(String[] args) {
		BankRepository repo = new BankRepository();
		TransactionService txService = new TransactionService();

		try {
			BankData loaded = PersistenceUtil.loadFile(DATA_FILE);
			if (loaded != null) {
				// initialise the repo and txService from loaded data;
				repo.initializeFrom(loaded.getAccounts(), loaded.getNextAccountSeq());
				txService.initializeFrom(loaded.getTransaction());
				System.out.println("Loaded persisted bank data Account : " + repo.getAccountsMap().size());
			} else {
				System.err.println("No previous data found starting from Fresh");
			}

		} catch (Exception e) {
			System.err.println("Failed to load persisted data: " + e.getMessage());
		}

		bankservice = new BankService(repo, txService);


		
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		while (running) {
			printMenu();
			String choice = sc.nextLine().trim();

			try {

				switch (choice) {
				case "1" -> createAccount(sc);
				case "2" -> deposit(sc);
				case "3" -> withdraw(sc);
				case "4" -> transfer(sc);
				case "5" -> viewBalance(sc);
				case "6" -> miniStatement(sc);
				case "7" -> {
					System.out.println("Existing... Saving data");
					saveandExit();
					running = false;
				}
				case "8" -> getallAccountDetails();
				default -> System.out.println("Invalid choice");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}

		}
		sc.close();
	}


	private static void getallAccountDetails() {
		bankservice.getAllaccountDetails();
	}


	private static void saveandExit() {
		try {

			BankData snapshot = bankservice.snapshotForPersistence();
			PersistenceUtil.saveBankData(snapshot, DATA_FILE);
			System.out.println("Data Saved to " + DATA_FILE.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("Failed to save data " + e.getMessage());
		}

	}

	private static void createAccount(Scanner sc) {
		try {
			System.out.println("Enter customer name");
			String name = sc.nextLine().toString();

			System.out.println("Enter the account type(SAVINGS/CURRENT):  ");
			String type = sc.nextLine().toString();

			System.out.print("Initial deposit: ");
			double deposit = Double.parseDouble(sc.nextLine().trim());

			AccountType acctype = AccountType.valueOf(type);

			int acctNo = bankservice.createAccount(name, acctype, deposit);
			System.out.println("Account Created with acount no" + acctNo);

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid Input : :" + e.getMessage());
			
		}
	}

	private static void deposit(Scanner sc) throws AccountNotFoundException {
			System.out.println(" Enter the account no : ");
			int acc = Integer.parseInt(sc.nextLine().trim());

			System.out.println(" Amount : ");
			double amount = Double.parseDouble(sc.nextLine().trim());
			bankservice.deposit(acc, amount);
			System.out.println("Deposite successfylly");
	}
	private static void withdraw(Scanner sc) throws Exception {
		System.out.println(" Enter the account no : ");
		int acc = Integer.parseInt(sc.nextLine().trim());

		System.out.println(" Amount : ");
		double amount = Double.parseDouble(sc.nextLine().trim());
		bankservice.withDrwan(acc, amount);
		System.out.println("withdraw successfylly");
	}
	
	private static void transfer(Scanner sc) throws AccountNotFoundException, Exception {
		System.out.println(" Enter the from account no : ");
		int fromAcc = Integer.parseInt(sc.nextLine().trim());
		System.out.println(" Enter the to account no : ");
		int ToAcc = Integer.parseInt(sc.nextLine().trim());
		System.out.println(" Enter the Amount u want to trasfer : ");
		double amount = Double.parseDouble(sc.nextLine().trim());
		bankservice.transfer(fromAcc,ToAcc,amount);
		System.out.println("Transfer successful.");

	}

	

	private static void viewBalance(Scanner sc) throws AccountNotFoundException {
		System.out.println(" Enter the from account no : ");
		int acc = Integer.parseInt(sc.nextLine().trim());
		Account account=bankservice.getAccount(acc);
		System.out.println(account);
		System.out.println(account.getBalace());
	}

	private static void miniStatement(Scanner sc) throws AccountNotFoundException
	{
		System.out.println(" Enter the from account no : ");
		int acc = Integer.parseInt(sc.nextLine().trim());
		bankservice.printMiniStatement(acc, 5);
	}
	

	
	

	private static void printMenu() {
		System.out.println("\n===== SmartBank Menu =====");
		System.out.println("1. Create Account");
		System.out.println("2. Deposit");
		System.out.println("3. Withdraw");
		System.out.println("4. Transfer");
		System.out.println("5. View Balance");
		System.out.println("6. Mini Statement");
		System.out.println("7. Exit");
		System.out.println("8. All Account Details");
		System.out.print("Enter choice: ");

	}
}
