package com.logicBank.repository;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MovementRepository {
	
	Connect connect = new Connect();
	//Cadena de text formatada amb espais per donar lloc a l'estructura de camps
	private final String MOVEMENTS_TABLE = "\nBalance           Concept                   Associated Entity        Date";
	
	//Insereix un nou moviment
	public void insertNewMovement(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}
	
	/* Funció auxiliar per mostrar per pantalla una linea separatòria entre
	 * els camps que esperem visualitzar a l'hora de consultar moviments i 
	 * cadascun dels registres obtinguts per 'SELECT' querys;
	 */
	private void showTableStructure() {
		System.out.println(MOVEMENTS_TABLE);
		for(int i = 0; i < MOVEMENTS_TABLE.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	/* Obté el saldo total d'un compte bancari;
	 */
	public double getBalance(String sqlQuery) {
		double balance = 0;
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			rs.next();
			balance = rs.getDouble(1);
		}catch(SQLException e) {
			e.getMessage();
		}
		return balance;
	}
	
	/* Obté tots els moviments d'un compte bancari i els printa per pantalla 
	 * seguint un format coherent amb l'estructura definida per la constant 
	 * 'MOVEMENTS_TABLE' definida al cap de la clase
	 * Retorna el total de moviments
	 */
	public void getAllMovements(String sqlQuery) {
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			showTableStructure();
			while(rs.next()) {
				System.out.print(rs.getDouble(4)+"\t\t");
				if(rs.getString(5).equals("imposicion")) {
					System.out.print(rs.getString(5)+"\t\t   ");
				}else {
					System.out.print(rs.getString(5)+"\t   ");
				}
				System.out.print(rs.getString(6)+"\t\t");
				System.out.print(rs.getDate(7));
				System.out.println();
			}
			rs.close(); //LO PONGO HOY A 24/03
			System.out.println("\n\n");
		}catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/* Mateix resultat que getAllMovements(String sqlQuery) però en aquest cas
	 * filtrat per dates
	 */
	public void getAllMovementsBetween(String sqlQuery) {
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			showTableStructure();
			while(rs.next()) {
				System.out.print(rs.getDouble(4)+"\t\t");
				if(rs.getString(5).equals("imposicion")) { //Això senzillament es fa per estilisme, per adaptar el resultat a l'alçada on está printat el nom del camp 'Concepte'
					System.out.print(rs.getString(5)+"\t\t   ");
				}else {
					System.out.print(rs.getString(5)+"\t   ");
				}
				System.out.print(rs.getString(6)+"\t\t");
				System.out.print(rs.getDate(7));
				System.out.println();
			}
			rs.close(); //LO PONGO HOY A 24/03
		}catch(SQLException e) {
			e.getMessage();
		}
	}
}
