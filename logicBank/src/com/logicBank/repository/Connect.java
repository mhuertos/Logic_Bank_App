package com.logicBank.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	public static final String CONTROLLER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/banco";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	
	/*
	 * Funció encarregada de la connexió entre el programa Java i la bbdd MySql
	 */
	public Connection connect() {
		Connection connection = null;
		try{
			Class.forName(CONTROLLER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		}catch(ClassNotFoundException e) {
			System.out.println("Something wrong with controller");
			e.printStackTrace();
		}catch(SQLException s) {
			System.out.println("Something wrong connecting to database.");
			s.printStackTrace();
		}
		return connection;
	}
	
	/*
	 * Funció que connecta amb la bbdd y retorna el conjunt de resultats
	 * obtinguts amb una consulta 'SELECT';
	 */
	public ResultSet executeSelect(String SQLQuery) {
		Connection cn = connect();
		
		try {
			Statement stm = cn.createStatement();
			ResultSet rs = stm.executeQuery(SQLQuery);
			return rs;
		}catch(SQLException e) {
			e.getMessage();
		}
		return null;
	}
	
	/*Funció que connecta amb la bbdd y trasllada una actualització de dades
	 * a partir d'una 'query' passada per paràmetre
	 */
	public void executeUpdate(String SQLQuery) {
		Connection cn = connect();
		try {
			Statement stm = cn.createStatement();
			stm.executeUpdate(SQLQuery);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
