package com.logicBank.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.logicBank.model.Card;

public class CardRepository {
	
	Connect connect = new Connect();
	
	/* Comprova que existeix alguna targeta 
	 * Retorna un booleà en funció de si s'han obtingut o no registres.
	 */
	public boolean hasCard(String sqlQuery) {
		boolean hasCard = false;
		try {	
			ResultSet rs = connect.executeSelect(sqlQuery);
			hasCard = rs.next(); //rs.next() dona true/false en funció de si obté o no resultats.
			rs.close(); //LO PONGO A DIA 24/03
		}catch(SQLException e) {
			e.getCause();
		}
		return hasCard;
	}
	
	/* Obté el conjunt de valors de cada camp de la taula 'Targeta' i els
	 * incorpra a una instància de la classe 'Card';
	 * Retorna una instància de la clase 'Card'
	 */
	public Card getCard(String sqlQuery) {
		Card tarjeta = new Card();
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);

			while(rs.next()) {
				tarjeta.setId(rs.getInt(1));
				tarjeta.setNombre(rs.getString(2));
				tarjeta.setTipo_targeta(rs.getString(3));
				tarjeta.setNumtargeta(rs.getString(4));
				tarjeta.setCreation_date(rs.getDate(5));
				tarjeta.setExpiration_date(rs.getDate(6));
				tarjeta.setNumSeguridad(rs.getString(8));
				tarjeta.setLimite(rs.getDouble(9));
				tarjeta.setTAE(rs.getDouble(10));
			}
			rs.close();
		}catch(SQLException e) {
			e.getCause();
		}
		return tarjeta;
	}
	
	/* Obté un conjunt de resultats a partir d'una 'query' passada per paràmetre
	 * Retorna un llistat de 'Strings' que representen algún atribut identificatiu de 
	 * la classe 'Card'
	 */
	public List<String> getCardNumbers(String sqlQuery){
		List<String> cardList = new ArrayList<String>();
		try {
			ResultSet rs = connect.executeSelect(sqlQuery);
			while(rs.next()) {
				cardList.add(rs.getString(1));
			}
			rs.close();
		}catch(SQLException e) {
			e.getCause();
		}
		return cardList;
	}
	
	/* Inserta un objecte de tipus card en la base de dates.
	 * La 'query' d'inserció s'obté de la mateixa clase 'Card'
	 */
	public void insertNewCard(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}
	
	/* Actualitza el atribut 'pin' d'una targeta
	 */
	public void updatePinCard(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}
	
	/* Elimina una targeta 
	 */
	public void deleteCard(String sqlQuery) {
		connect.executeUpdate(sqlQuery);
	}
}
