package com.logicBank.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import com.logicBank.model.Account;

public class AccountRepository {
	
	Connect connect = new Connect(); //Importem una instància de la classe Connect;
	
	/* Obté un conjunt de resultats a partir d'una 'query' passada per paràmetre
	 * Retorna un llistat de 'Strings' que representen algún atribut identificatiu de 
	 * la classe 'Account'
	 */
	public List<String> getListOfAccounts(String sqlQuery){
		ArrayList<String> listOfAccounts = new ArrayList<String>();
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			while(rs.next()) {
				listOfAccounts.add(rs.getString(1));
			}
			rs.close(); //LA PONGO A DÍA 24/03
		}catch(SQLException e) {
			e.getMessage();
		}
		return listOfAccounts;
	}
	
	
	/* A partir d'una 'query' passada per paràmetre i 
	 * programada per obtenir 1 sol registre, llegeix el valor de cadascún dels camps i
	 * els utilitza per modificar els atributs d'un objecte de la classe 'Account' 
	 * Retorna una instància del tipus Account.
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
	
	/* Comprova si existeix o no un compte bancari, en funció de si la 'query' entregada
	 * per paràmetre genera resultats o no.
	 * Retorna un booleà.
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
	 * La 'query' d'inserció s'obté de la mateixa clase 'Account'
	 */
	public void insertNewCount(Account account) {
		connect.executeUpdate(account.getQuery());
	}

	/* Obté l'id del compte. 
	 * Aquest id nomès es creat a la bbdd un cop inserim un nou compte i ho necessitem 
	 * per operar amb eficàcia en algunes part del programa.
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
