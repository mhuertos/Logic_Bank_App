package com.logicBank.service;

import java.sql.Date;

import com.logicBank.model.Account;
import com.logicBank.model.Client;
import com.logicBank.repository.ClientRepository;

public class ClientService {
	
	private ClientRepository clientRepository = new ClientRepository();
		
	public int getClientId(Client client) {
		String sqlquery = String.format("SELECT c.idCliente FROM cliente c WHERE c.nif = '%s'", client.getNif());
		return clientRepository.getClientId(sqlquery);
	}
	
	public boolean clientExists(String nif) {
		String sqlString = String.format("SELECT c.nombre FROM cliente c WHERE c.nif = '%s'", nif);
		return clientRepository.clientExists(sqlString);
	}
	
	public Client getClientByNif(String nif) {
		String sqlString = String.format("SELECT * FROM cliente WHERE nif = '%s'", nif);
		return clientRepository.getClient(sqlString);
	}
	
	public Client getClientByAccount(Account account) {
		String sqlString = String.format("SELECT c.idCliente, c.nif, c.nombre, c.apellidos, c.fecha_nacimiento"
				+ " FROM (cliente c LEFT JOIN titular_linea t ON c.idCliente=t.idCliente) "
				+ "LEFT JOIN cuenta cu ON t.idCuenta = cu.idCuenta "
				+ "WHERE cu.iban = '%s'", account.getNumCuenta());
		return clientRepository.getClient(sqlString);
	}
	
	public void insertNewClient(Client client) {
		clientRepository.insertNewClient(client.getQuery());
	}
	
	public void createAccountToClient(int idClient, int idAccount) {
		long millis = System.currentTimeMillis();
		String sqlQuery = String.format("INSERT INTO titular_linea VALUES ( %d, %d, '%tF')",
				idClient, idAccount, new Date(millis));
		clientRepository.createAccountToClient(sqlQuery);
	}

	//PARA QUE UTILIZO ESTO??
	public void deleteClient(int idClient) {
		String sqlQuery = String.format("DELETE FROM cliente WHERE idCliente = '%s'", idClient);
		clientRepository.deleteClient(sqlQuery);
		
	}
}
