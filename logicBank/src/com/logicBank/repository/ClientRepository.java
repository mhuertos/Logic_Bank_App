package com.logicBank.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.logicBank.model.Client;

public class ClientRepository {
	
	Connect connect = new Connect();
	
	/* Comprova si un client existeix o no a la base de dades.
	 * En funció de si la 'query' obté o no resultats es retorna un resultat
	 * Retorna un booleà
	 */
	public boolean clientExists(String sqlString) {
		try {
			ResultSet rs = connect.executeSelect(sqlString);
			rs.next();
			if(rs.getString(1) != null) {	
				return true;
			}
			rs.close();
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return false;
	}
	
	/* Insereix un noy usuari
	 */
	public void insertNewClient(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}
	
	/* Obté l'id d'un client ja inserit a la bbdd
	 * Aquest id nomès es creat a la bbdd un cop inserim un nou compte i ho necessitem 
	 * per operar amb eficàcia en algunes part del programa.
	 */
	public int getClientId(String sqlQuery) {
		int id = 0;
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			rs.next();
			id = rs.getInt(1);
			rs.close(); //LO PONGO HOY A 24/03
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return id;
	}
	
	/* A partir d'una 'query' passada per paràmetre i 
	 * programada per obtenir 1 sol registre, llegeix el valor de cadascún dels camps i
	 * els utilitza per modificar els atributs d'un objecte de la classe 'Client' 
	 * Retorna una instància del tipus Client.
	 */
	public Client getClient(String sqlQuery) {
		Client client = new Client();
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			rs.next();
			client.setId(rs.getInt(1));
			client.setNif(rs.getString(2));
			client.setName(rs.getString(3));
			client.setLastnames(rs.getString(4));
			client.setData_of_birth(rs.getDate(5));
			rs.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return client;
	}
	
	/* Insereix la taula 'titular_linea' que descriu la titularitat d'un client
	 * sobre un compte bancari concret. En aquest sentit, actualitza la taula que associa
	 * clients amb comptes bancaris
	 */
	public void createAccountToClient(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}

	//Elimina un client de la bbdd
	public void deleteClient(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}
}
