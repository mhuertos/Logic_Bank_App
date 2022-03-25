package com.logicBank.security;

import java.util.Scanner;

import com.logicBank.model.Client;
import com.logicBank.service.ClientService;
/*
 * Aquesta classe inclou un m�tode per logejar un nou usuari
 */
public class Login {
	
	private final Scanner scan = new Scanner(System.in);
	private ClientService clientService = new ClientService();

	public Login() {
	}
	
	/* Demana el DNI a l'usuari. Comprova la seva exist�ncia. Si existeix
	 * obt� la resta de dades del client directament de la bbdd.
	 * Printa un missatge de benvinguda.
	 * Retorna una inst�ncia de la classe 'Client' per operar amb ell.
	 */
	public Client userLogin() {
		Client client = null;
		System.out.print("Enter your DNI: ");
		String nif = scan.next();
		scan.nextLine();
		if(clientService.clientExists(nif)) {
			client = clientService.getClientByNif(nif);
			System.out.println("\n\n >> Welcome "+client.getName()+"! <<\n\n");
		}else {		
			System.err.println("INVALID LOGIN: Given NIF wasn't found on database.");
		}
		return client;
	}
}
