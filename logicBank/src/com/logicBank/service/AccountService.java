package com.logicBank.service;

import java.util.List;

import com.logicBank.model.Account;
import com.logicBank.model.Client;
import com.logicBank.repository.AccountRepository;

public class AccountService {
	
	private AccountRepository accountRepository = new AccountRepository();
	
	
	public void insertNewAccount(Account account) {
		accountRepository.insertNewCount(account);
	}
	
	public List<String> getListOfIbanFromAccounts(Client client){
		String sqlQuery = "select c.IBAN from cuenta c LEFT JOIN titular_linea t  "
				+ "ON c.idCuenta = t.idCuenta WHERE t.idCliente = "+client.getId();
		return accountRepository.getListOfAccounts(sqlQuery);
	}
	
	public Account getAccountByIban(String iban) {
		String sqlQuery = String.format("SELECT * FROM cuenta WHERE IBAN = '%s'", iban);
		return accountRepository.getAccount(sqlQuery);
	}
	
	public int getAccountId(Account account) {
		String sqlQuery = "SELECT c.idCuenta FROM cuenta c WHERE c.IBAN = '"+account.getNumCuenta()+"'";
		return accountRepository.getAccountId(sqlQuery);
	}
	
	public boolean accountExists(String iban) {
		String sqlQuery = String.format("SELECT * FROM cuenta WHERE IBAN = '%s'", iban);
		return accountRepository.accountExists(sqlQuery);
	}

	public Account getAccountByNameAndClient(Client client, String nombre) {
		String sqlQuery = String.format("SELECT c.* from cuenta c LEFT JOIN titular_linea t "
				+ "ON c.idCuenta=t.idCuenta WHERE t.idCliente = %d AND c.nombre= '%s'", 
				client.getId(), nombre);
		System.out.println(sqlQuery);
		return accountRepository.getAccount(sqlQuery);
	}

	public Account getOneAccount(Client client) {
		String sqlQuery = String.format("SELECT c.* FROM cuenta c LEFT JOIN titular_linea t "
				+ "ON c.idCuenta=t.idCuenta WHERE t.idCliente = %d", client.getId());
		return accountRepository.getAccount(sqlQuery);
	}
}
