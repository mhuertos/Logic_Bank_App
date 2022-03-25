package com.logicBank.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import com.logicBank.model.Account;

public class AccountRepository {
	
	Connect connect = new Connect(); //Importem una inst�ncia de la classe Connect;
	
	/* Obt� un conjunt de resultats a partir d'una 'query' passada per par�metre
	 * Retorna un llistat de 'Strings' que representen alg�n atribut identificatiu de 
	 * la classe 'Account'
	 */
	public List<String> getListOfAccounts(String sqlQuery){
		ArrayList<String> listOfAccounts = new ArrayList<String>();
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			while(rs.next()) {
				listOfAccounts.add(rs.getString(1));
			}
			rs.close(); //LA PONGO A D�A 24/03
		}catch(SQLException e) {
			e.getMessage();
		}
		return listOfAccounts;
	}
	
	
	/* A partir d'una 'query' passada per par�metre i 
	 * programada per obtenir 1 sol registre, llegeix el valor de cadasc�n dels camps i
	 * els utilitza per modificar els atributs d'un objecte de la classe 'Account' 
	 * Retorna una inst�ncia del tipus Account.
	 */
	public Account getAccount(String sqlString) {
		Account account = new Account();
		try {
			ResultSet rs = connect.executeSelect(sqlString);
			while(rs.next()) {
				account.setId(rs.getInt(1));
				account.setNombre(rs.getString(2));
				account.setDate_create(rs.getDate(3));
				account.setNumCuenta(rs.getString(4));
				account.setTipo_cuenta(rs.getString(5));
			}
			rs.close();
		}catch(SQLException e) {
			e.getMessage();
		}
		return account;
	}
	
	/* Comprova si existeix o no un compte bancari, en funci� de si la 'query' entregada
	 * per par�metre genera resultats o no.
	 * Retorna un boole�.
	 */
	public boolean accountExists(String sqlQuery) {
		boolean exists = false;
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			if(rs != null) {
				exists = true;
				rs.close();
			}
		}catch(SQLException e) {
			e.getMessage();
		}
		return exists;
	}

	/* Inserta un objecte de tipus account en la base de dates.
	 * La 'query' d'inserci� s'obt� de la mateixa clase 'Account'
	 */
	public void insertNewCount(Account account) {
		connect.executeUpdate(account.getQuery());
	}

	/* Obt� l'id del compte. 
	 * Aquest id nom�s es creat a la bbdd un cop inserim un nou compte i ho necessitem 
	 * per operar amb efic�cia en algunes part del programa.
	 */
	public int getAccountId(String sqlQuery) {
		int id = 0;
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			rs.next();
			id = rs.getInt(1);
			rs.close();
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return id;
	}
}
