package com.logicBank.service;

import java.sql.Date;
import java.util.Locale;

import com.logicBank.model.Account;
import com.logicBank.model.Client;
import com.logicBank.repository.MovementRepository;
import com.logicBank.utiles.Utils;

public class MovementService {
	
	private MovementRepository movementRepository = new MovementRepository();

	private ClientService clientService = new ClientService();
	private AccountService accountService = new AccountService();
	
	
	//ingreso (aumento)
	public void makeNewDeposit(Client client, Account account) {
		System.out.println("Please, enter hoy much money you want to insert");
		double amount = Utils.askForQuantity();
		long millis = System.currentTimeMillis();
		Date date_movement = new Date(millis);
		String sqlQuery = String.format(Locale.ROOT,
				"INSERT INTO movimiento VALUES (DEFAULT, %d, %d, %.2f, 'imposicion', 'privado', '%tF', 2)",
				client.getId(), account.getId(), amount, date_movement);
		movementRepository.insertNewMovement(sqlQuery);
		System.out.println("Operation completed successfully");
	}
		
	
	//extracción cajero (descuento)
	public void makeNewExtract(Client client, Account account) {
		System.out.println("Please, enter hoy much money you want to extract");
		double amount = Utils.askForQuantity();
		if(getTotalBalance(/*client, */account) > amount) {
			amount *= -1;
			long millis = System.currentTimeMillis();
			Date date_movement = new Date(millis);
			String sqlQuery = "INSERT INTO movimiento VALUES "
					+ "(DEFAULT, "+client.getId()+", "+account.getId()+", "+amount+", 'extraccion_cajero', 'privado', '"+
					date_movement+"', 5)";
			movementRepository.insertNewMovement(sqlQuery);
			System.out.println("Operation completed successfully");
		}else {
			System.err.println("You don't have such amount of money");
		}
	}
		
		//transferencia (descuento)
		public void sendNewTransfer(Client client, Account account, double amount) {
			
			if(getTotalBalance(/*client, */account) > amount) {
				amount *= -1;
				long millis = System.currentTimeMillis();
				Date date_movement = new Date(millis);
				String sqlQuery = String.format(Locale.ROOT, "INSERT INTO movimiento VALUES (DEFAULT, %d, %d, %.2f, "+
					"'transferencia_bancaria', 'privado', '%tF', 1)", 
					client.getId(), account.getId(), amount, date_movement);
				movementRepository.insertNewMovement(sqlQuery);
				System.out.println("Operation completed successfully");
			}else {
				System.err.println("You don't have such amount of money");
			}
		}
		
		public void receiveNewTransfer(Client client, Account account, double amount) {
			long millis = System.currentTimeMillis();
			Date date_movement = new Date(millis);
			String sqlQuery = String.format(Locale.ROOT, "INSERT INTO movimiento VALUES (DEFAULT, %d, %d, %.2f, "+
					"'ingreso_bancario', 'privado', '%tF', 2)", 
					client.getId(), account.getId(), amount, date_movement);
			movementRepository.insertNewMovement(sqlQuery);
		}
		
		
		public void makeTransfer(Client fromClient, Account fromAccount) {
			System.out.println("Please, enter hoy much money you want to transfer");
			double amount = Utils.askForQuantity();
			if(getTotalBalance(/*fromClient, */fromAccount) > amount) {
				System.out.println("Enter IBAN which you want to transfer: ");
				String iban = Utils.askForString("IBAN", 28);
				if(accountService.accountExists(iban)) {
					Account toAccount = accountService.getAccountByIban(iban);
					if(toAccount == null) { 
						System.err.println("There is not any count with this IBAN");
					}else if(toAccount.getId() == fromAccount.getId()){
						System.err.println("This operation is not valid");
					}else {
						Client toClient = clientService.getClientByAccount(toAccount);
						System.out.println("So, you want to transfer "+amount+" to "+
								toClient.getName()+ " "+toClient.getLastnames());
						if(Utils.askConfirmation()) {
							sendNewTransfer(fromClient, fromAccount, amount);
							receiveNewTransfer(toClient, toAccount, amount);
							System.out.println("Transfer completed");
						}else {
							System.out.println("Transfer canceled");
						}
					}
				}else {
					System.err.println("This iban doesn't exists");
				}
			}else {
				System.err.println("You don't have such amount of money");
			}	
		}
		
		//CREO QUE EL PARÁMETRO 'CLIENT' NO LO NECESITO EN ESTA FUNCIÓN
		public double getTotalBalance(/*Client client, */Account account) {
			String sqlQuery = String.format("SELECT sum(saldo) FROM movimiento WHERE idCuenta = %d", account.getId());
			double saldo = movementRepository.getBalance(sqlQuery);
			return saldo;
		}
		
		public void printTotalBalance(/*Client client,*/ Account account) {
			double balance = getTotalBalance(/*client, */account);
			System.out.println("Currently, your account has: "+balance+"€.");
		}
		
		public void getAllMovementsBetweenDates(Client client, Account account) {
			Date from_date = Utils.askForDate("since");
			Date until_date = Utils.askForDate("until");
			String sqlQuery = String.format("SELECT * FROM movimiento WHERE idCuenta = %d "
					+ "AND idCliente = %d AND fecha_movimiento BETWEEN '%tF' AND '%tF'", 
					account.getId(), client.getId(), from_date, until_date);			
			movementRepository.getAllMovementsBetween(sqlQuery);
		}

		public void getAllMovements(Client client, Account account) {
			String sqlQuery = String.format("SELECT * FROM movimiento WHERE idCuenta = %d AND idCliente = %d", 
					account.getId(), client.getId());
			movementRepository.getAllMovements(sqlQuery);
		}
}
