package com.logicBank.service;

import java.util.List;
import java.util.Locale;

import com.logicBank.model.Account;
import com.logicBank.model.Card;
import com.logicBank.model.Client;
import com.logicBank.repository.CardRepository;
import com.logicBank.utiles.Utils;

public class CardService {
	
	private CardRepository cardRepository = new CardRepository();
	
	public Card getCard(Client client, Account account) {
		String sqlQuery = String.format("select * from targeta WHERE idCliente = %d AND idCuenta = %d", 
				client.getId(), account.getId());
		return cardRepository.getCard(sqlQuery);
	}
	
	public void updateCardPin(Card card) {
		String newPin = Utils.askForNewPin(Card.PIN_LENGTH); //demanem un nou pin amb 4 digits;
		String sqlQuery = String.format("UPDATE targeta SET pin = '%s' WHERE IdTargeta = %d", newPin, card.getId());
		cardRepository.updatePinCard(sqlQuery);
		System.out.println("Your PIN has been updated successfully");
	}
	
	public boolean clientHasCard(Client client, Account account) {
		String sqlQuery = String.format("select * from targeta WHERE idCliente = %d AND idCuenta = %d", 
				client.getId(), account.getId());
		return cardRepository.hasCard(sqlQuery);
	}
	
	public List<String> getListOfCardNames(Client client, Account account){
		String sqlQuery = String.format("select nombreTargeta from targeta WHERE idCliente = %d AND idCuenta = %d", 
				client.getId(), account.getId());
		return cardRepository.getCardNumbers(sqlQuery);
	}
	
	public Card getCardByCardName(String cardName) {
		String sqlQuery = String.format("select * from targeta t WHERE nombreTargeta = '%s'", cardName);
		return cardRepository.getCard(sqlQuery);
	}
	
	public void createNewCreditCard(Client client, Account account, Card card){
		String sqlQuery = String.format(Locale.ROOT, "INSERT INTO targeta VALUES "
					+ "(DEFAULT, '%s', '%s', '%s', '%tF', '%tF', '%s', '%s', %.2f, %,.2f, %d, %d)", 
					card.getNombre(), card.getTipo_targeta(), card.getNumtargeta(),
					card.getCreation_date(), card.getExpiration_date(), card.getPin(),
					card.getNumSeguridad(),  card.getLimite(), card.getTAE(),
					client.getId(), account.getId());
		cardRepository.insertNewCard(sqlQuery);
		System.out.println("Your card has been successfully created.");
	}
	
	public void deleteCard(Client client, Account account, Card card) {
		String sqlString = String.format("DELETE FROM targeta "
				+ "WHERE idTargeta = %d AND idCliente = %d AND idCuenta = %d", 
				card.getId(), client.getId(), account.getId());
		cardRepository.deleteCard(sqlString);
		System.out.println("Your card has been successfully deleted.");
	}
}
